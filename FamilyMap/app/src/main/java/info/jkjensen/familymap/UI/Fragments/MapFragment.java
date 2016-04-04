package info.jkjensen.familymap.UI.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
    private FamilyMap mFamilyMap;

    com.amazon.geo.mapsv2.SupportMapFragment mMapFragment;
    AmazonMap mAmazonMap;
    LinearLayout mPersonLayout;

    ArrayList<Marker> mMarkers;

    URL mEventUrl;
    URL mPersonUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mFamilyMap = FamilyMap.getInstance();
        mMarkers = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
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
            }
        });
        //Call an asyncTask to get events/people associated with user
        EventRequestTask task = new EventRequestTask();
        task.execute();
        PersonRequestTask ptask = new PersonRequestTask();
        ptask.execute();

        //TODO: add a listener for the person layout
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

    private void populateMarkers() {
        if(mFamilyMap.getUserEvents() == null) return;
        for(FamilyMapEvent current : mFamilyMap.getUserEvents()){
            LatLng point = new LatLng(current.getLatitude(), current.getLongitude());
            MarkerOptions opt = new MarkerOptions()
                    .position(point)
                    .title("Test")
                    .snippet(point.toString());
            Marker m = mAmazonMap.addMarker(opt);
            mMarkers.add(m);
        }
    }

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
                    event.setDescendant(current.getString("descendant"));

                    result.add(event);
                }
            } catch(JSONException jsone){
                Log.e("json", jsone.getMessage());
            }
            return result;
        }
    }

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
                    person.setSpouseID(current.getString("spouse"));

                    result.add(person);
                }
            } catch(JSONException jsone){
                Log.e("json", jsone.getMessage());
            }
            return result;
        }
    }
}
