package info.jkjensen.familymap.UI.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import info.jkjensen.familymap.Model.FamilyMapEvent;
import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.Model.Person;
import info.jkjensen.familymap.R;
import info.jkjensen.familymap.UI.PersonActivity;
import info.jkjensen.familymap.WebTools.HttpClient;

/**
 * Created by Jk on 3/21/2016.
 */
public class MapFragment extends Fragment {

    /**A reference to the model for the app*/
    private FamilyMap mFamilyMap;

    //View layouts
    com.amazon.geo.mapsv2.SupportMapFragment mMapFragment;
    AmazonMap mAmazonMap;
    LinearLayout mPersonLayout;
    TextView mNameView;
    TextView mInfoView;
    private ImageView mGenderIcon;

    /**List of markers on the map*/
    HashMap<Integer, FamilyMapEvent> mMarkerEvents;
    ArrayList<Marker> mMarkers;

    /**URL for event GET*/
    URL mEventUrl;
    /**URL for person GET*/
    URL mPersonUrl;

    FamilyMapEvent mCurrentEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mFamilyMap = FamilyMap.getInstance();
        mMarkers = new ArrayList<>();
        mMarkerEvents = new HashMap<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mNameView = (TextView) v.findViewById(R.id.marker_person_name);
        mInfoView = (TextView) v.findViewById(R.id.marker_information);
        mGenderIcon = (ImageView) v.findViewById(R.id.selected_gender_icon);

        try {
            mEventUrl = new URL(
                    "http://" + mFamilyMap.getHostIP() + ":" + mFamilyMap.getPort() + "/event/");
            mPersonUrl = new URL(
                    "http://" + mFamilyMap.getHostIP() + ":" + mFamilyMap.getPort() + "/person/");
        } catch(MalformedURLException me){
            Log.e("jk_http", me.getMessage());
        }
        FragmentManager fm = getChildFragmentManager();

        mMapFragment =
                (com.amazon.geo.mapsv2.SupportMapFragment)
                        getChildFragmentManager().findFragmentById(
                                R.id.map);
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(AmazonMap amazonMap) {
                mAmazonMap = amazonMap;
                // TODO: Set up autoZoom and center on the selected marker if it exists
                //IE  amazonMap.animateCamera(CameraUpdateFactory.newLatLngZoom(COFFEE_LATLNG, COFFEE_ZOOM));
                mAmazonMap.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        try {
                            mCurrentEvent = mMarkerEvents.get(marker.hashCode());

                            Person eventPerson = mFamilyMap.getPersonFromEvent(mCurrentEvent);
                            if (eventPerson == null) throw new Exception("Failed to find person.");
                            mFamilyMap.setCurrentPerson(eventPerson);

                            updateUI(eventPerson);
                        } catch(Exception e){
                            Log.e("map", e.getMessage());
                        }


                        return false;
                    }
                });
            }
        });

        //Call asyncTasks to get events/people associated with user
        EventRequestTask eventRequestTask = new EventRequestTask();
        eventRequestTask.execute();
        PersonRequestTask personRequestTask = new PersonRequestTask();
        personRequestTask.execute();

        mFamilyMap.resetCurrentPerson();
        mPersonLayout = (LinearLayout) v.findViewById(R.id.layout_person);
        mPersonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void updateUI(Person person) {
        mNameView.setText(mFamilyMap.getCurrentPerson().getFirstName() + " " +
                mFamilyMap.getCurrentPerson().getLastName());
        mInfoView.setText(
                mCurrentEvent.getDescription() +
                ": " + mCurrentEvent.getCity() + ", " +
                        mCurrentEvent.getCountry() + " (" + mCurrentEvent.getYear() + ")");
        if(person.getGender().equals("m")) {
            mGenderIcon
                    .setImageDrawable(ContextCompat.getDrawable(getActivity(),
                            R.drawable.ic_gender_male_white_48dp));
        } else{
            mGenderIcon
                    .setImageDrawable(ContextCompat.getDrawable(getActivity(),
                            R.drawable.ic_gender_female_white_48dp));
        }
    }

    /**
     * Populates the map with all events currently in the FamilyMap model
     */
    private void populateMarkers() {
        if(mFamilyMap.getUserEvents() == null) return;
        for(FamilyMapEvent current : mFamilyMap.getUserEvents()){
            LatLng point = new LatLng(current.getLatitude(), current.getLongitude());
            MarkerOptions opt = new MarkerOptions()
                    .position(point);
//                    .color?
            Marker m = mAmazonMap.addMarker(opt);
            mMarkers.add(m);
            mMarkerEvents.put(m.hashCode(), current);
        }
    }

    /**
     * An AsyncTask to GET all events associated with the current user
     */
    private class EventRequestTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                String result = new HttpClient()
                        .get(mEventUrl, mFamilyMap.getAuthToken());
                if(result == "error"){
                    throw new Exception("Failed to connect, check your IP");
                }
                JSONObject response = new JSONObject(result);
                Log.i("jk_http", response.toString(2));

                return result;
            } catch (Exception e){
                Log.e("jk_http", e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            try {
                JSONObject response = new JSONObject(message);
                JSONArray eventArray = response.getJSONArray("data");
                mFamilyMap.setUserEvents(parseJSONIntoEventArray(eventArray));
            }catch (JSONException jsone){
                Log.e("json", jsone.getMessage());
            }

            //Populate the map with pins for each event
            populateMarkers();

        }

        /**
         * Parses the JSON from the GET into an array of events that the model can use
         * @param eventArray the JSONArray to parse
         * @return an ArrayList of FamilyMapEvents
         */
        protected ArrayList<FamilyMapEvent> parseJSONIntoEventArray(JSONArray eventArray){
            ArrayList<FamilyMapEvent> result = new ArrayList<>();
            try {
                for (int i = 0; i < eventArray.length(); i++) {
                    JSONObject current = eventArray.getJSONObject(i);

                    FamilyMapEvent event = new FamilyMapEvent();
                    event.setEventId(current.getString("eventID"));
                    event.setPersonId(current.getString("personID"));
                    event.setLatitude(Float.parseFloat(current.getString("latitude")));
                    event.setLongitude(Float.parseFloat(current.getString("longitude")));
                    event.setCountry(current.getString("country"));
                    event.setCity(current.getString("city"));
                    event.setDescription(current.getString("description"));
                    event.setYear(current.getString("year"));
                    event.setDescendant(current.getString("descendant"));

                    result.add(event);
                }
            } catch(JSONException jsone){
                Log.e("json", jsone.getMessage());
            }
            return result;
        }
    }

    /**
     * An AsyncTask to GET all persons associated with the current user
     */
    private class PersonRequestTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                String result = new HttpClient()
                        .get(mPersonUrl, mFamilyMap.getAuthToken());
                if(result == "error"){
                    throw new Exception("Failed to connect, check your IP");
                }
                JSONObject response = new JSONObject(result);
                Log.i("jk_http", response.toString(2));

                return result;
            } catch (Exception e){
                Log.e("jk_http", e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            try {
                JSONObject response = new JSONObject(message);
                JSONArray personArray = response.getJSONArray("data");
                mFamilyMap.setUserPersons(parseJSONIntoPersonArray(personArray));
            }catch (JSONException jsone){
                Log.e("json", jsone.getMessage());
            }
        }

        /**
         * Parses the JSON from the GET into an array of Persons that the model can use
         * @param personArray the JSONArray to parse
         * @return an ArrayList of Persons
         */
        protected ArrayList<Person> parseJSONIntoPersonArray(JSONArray personArray){
            ArrayList<Person> result = new ArrayList<>();
            try {
                for (int i = 0; i < personArray.length(); i++) {
                    JSONObject current = personArray.getJSONObject(i);

                    Person person = new Person();
                    person.setDescendant(current.getString("descendant"));
                    person.setPersonID(current.getString("personID"));
                    person.setFirstName(current.getString("firstName"));
                    person.setLastName(current.getString("lastName"));
                    person.setGender(current.getString("gender"));
                    if(current.has("spouse")) {
                        person.setSpouseID(current.getString("spouse"));
                    }
                    if(current.has("father")){
                        person.setFatherID(current.getString("father"));
                    }
                    if(current.has("mother")){
                        person.setMotherID(current.getString("mother"));
                    }

                    result.add(person);
                }
            } catch(JSONException jsone){
                Log.e("json", jsone.getMessage());
            }
            return result;
        }
    }
}
