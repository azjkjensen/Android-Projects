package info.jkjensen.familymap.UI.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.amazon.geo.mapsv2.model.Polyline;
import com.amazon.geo.mapsv2.model.PolylineOptions;

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
import info.jkjensen.familymap.UI.MainActivity;
import info.jkjensen.familymap.UI.PersonActivity;
import info.jkjensen.familymap.WebTools.HttpClient;

/**
 * Created by Jk on 3/21/2016.
 */
public class MapFragment extends Fragment {
    private boolean firstVisit = true;

    /**A reference to the model for the app*/
    private FamilyMap mFamilyMap;

    //View layouts
    com.amazon.geo.mapsv2.SupportMapFragment mMapFragment;
    AmazonMap mAmazonMap;
    RelativeLayout mPersonLayout;
    TextView mNameView;
    TextView mInfoView;
    private ImageView mGenderIcon;

    /**List of markers on the map*/
    ArrayList<Marker> mMarkers;
    HashMap<Integer, FamilyMapEvent> mMarkerEvents;
    private Marker mSelectedMarker;
    private FamilyMapEvent mSelectedEvent;
    /**List of lines on the map*/
    private ArrayList<Polyline> mPolyLines;

    /**URL for event GET*/
    URL mEventUrl;

    /**URL for person GET*/
    URL mPersonUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mFamilyMap = FamilyMap.getInstance();
        mMarkerEvents = new HashMap<>();
        mMarkers = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mNameView = (TextView) v.findViewById(R.id.marker_person_name);
        mInfoView = (TextView) v.findViewById(R.id.marker_information);
        mGenderIcon = (ImageView) v.findViewById(R.id.selected_gender_icon);

        mPolyLines = new ArrayList<>();

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
                mFamilyMap.setAmazonMap(mAmazonMap);

                mAmazonMap.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        try {
                            mSelectedEvent = mMarkerEvents.get(marker.hashCode());
                            mSelectedMarker = marker;

                            Person eventPerson = mFamilyMap.getPersonFromEvent(mSelectedEvent);
                            if (eventPerson == null) throw new Exception("Failed to find person.");
                            mFamilyMap.setCurrentPerson(eventPerson);

                            updateUI(eventPerson);
                            drawMapLines();
                        } catch (Exception e) {
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
        mPersonLayout = (RelativeLayout) v.findViewById(R.id.layout_person);
        mPersonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                firstVisit = false;
                startActivity(intent);
            }
        });

        return v;
    }



    private void updateUI(Person person) {
        mNameView.setText(mFamilyMap.getCurrentPerson().getFirstName() + " " +
                mFamilyMap.getCurrentPerson().getLastName());
        mInfoView.setText(mSelectedEvent.getFormattedDescription());
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
    private Marker getMarkerFromEvent(){
        int markerID = 0;
        for(int i : mMarkerEvents.keySet()){
            FamilyMapEvent e = mMarkerEvents.get(i);

            if(mSelectedEvent.equals(e)){
                markerID = i;
                break;
            }
        }

        for(Marker m : mMarkers){
            if(m.hashCode() == markerID){
                return m;
            }
        }
        return null;
    }

    /**
     * Populates the map with all events currently in the FamilyMap model
     */
    public void populateMarkers() {
        if(mFamilyMap.getUserEvents() == null) return;
        mMarkers.clear();
        for(FamilyMapEvent current : mFamilyMap.getUserEvents()){
            LatLng point = new LatLng(current.getLatitude(), current.getLongitude());

            float hue = mFamilyMap.getEventColor(current);

            MarkerOptions opt = new MarkerOptions()
                    .position(point)
                    .icon(BitmapDescriptorFactory.defaultMarker(hue));
            Marker m = mAmazonMap.addMarker(opt);
            mMarkerEvents.put(m.hashCode(), current);
            mMarkers.add(m);
        }
    }

    public void drawMapLines() {
        for(Polyline pl : mPolyLines){
            pl.remove();
        }
        mPolyLines.clear();

        //Life Story Lines
        if(mFamilyMap.showLifeStoryLines()){

            Person currentPerson = mFamilyMap.getPersonFromEvent(mSelectedEvent);
            ArrayList<FamilyMapEvent> lifeStory = new ArrayList<>();
            ArrayList<Object> objectList = mFamilyMap.getLifeEvents(currentPerson.getPersonID());
            ArrayList<LatLng> points = new ArrayList<>();
            for(Object o : objectList){
                FamilyMapEvent event = (FamilyMapEvent) o;
                points.add(new LatLng(event.getLatitude(), event.getLongitude()));
                PolylineOptions opt = new PolylineOptions()
                        .addAll(points)
                        .color(mFamilyMap.getLifeStoryColor())
                        .width(5f);
                Polyline polyline = mAmazonMap.addPolyline(opt);
                mPolyLines.add(polyline);
            }
        }

        //Family Tree Lines
        // TODO: Make lines get progressively smaller
        if(mFamilyMap.showFamilyTreeLines()){
            Person currentPerson = mFamilyMap.getPersonFromEvent(mSelectedEvent);
            if(currentPerson.hasFather()) {
                ArrayList<FamilyMapEvent> paternalLine = new ArrayList<>();
                paternalLine.add(mSelectedEvent);
                paternalLine.addAll(mFamilyMap.getAncestors(currentPerson.getFatherID()));

                ArrayList<LatLng> points = new ArrayList<>();

                for(FamilyMapEvent event : paternalLine){
                    points.add(new LatLng(
                            event.getLatitude(), event.getLongitude()));
                }
                PolylineOptions opt = new PolylineOptions()
                        .addAll(points)
                        .color(mFamilyMap.getFamilyTreeColor())
                        .width(5f);
                Polyline polyline = mAmazonMap.addPolyline(opt);
                mPolyLines.add(polyline);
            }
            if(currentPerson.hasMother()){
                ArrayList<FamilyMapEvent> maternalLine = new ArrayList<>();
                maternalLine.add(mSelectedEvent);
                maternalLine.addAll(mFamilyMap.getAncestors(currentPerson.getMotherID()));

                ArrayList<LatLng> points = new ArrayList<>();

                for(FamilyMapEvent event : maternalLine){
                    points.add(new LatLng(
                            event.getLatitude(), event.getLongitude()));
                }
                PolylineOptions opt = new PolylineOptions()
                        .addAll(points)
                        .color(mFamilyMap.getFamilyTreeColor())
                        .width(5f);
                Polyline polyline = mAmazonMap.addPolyline(opt);
                mPolyLines.add(polyline);
            }
        }

        //Spouse Lines
        if(mFamilyMap.showSpouseLines()){
            ArrayList<LatLng> points = new ArrayList<>();
            Person currentPerson = mFamilyMap.getPersonFromEvent(mSelectedEvent);
            String spouseId = mFamilyMap.getSpouseID(currentPerson.getPersonID());

            Person currentPersonsSpouse = mFamilyMap.getPersonByID(spouseId);
            FamilyMapEvent earliestSpouseEvent = mFamilyMap.getEarliestEvent(currentPersonsSpouse);
            points.add(new LatLng(
                    earliestSpouseEvent.getLatitude(), earliestSpouseEvent.getLongitude()));
            points.add(new LatLng(
                    mSelectedEvent.getLatitude(), mSelectedEvent.getLongitude()));
            PolylineOptions opt = new PolylineOptions()
                    .addAll(points)
                    .color(mFamilyMap.getSpouseStoryColor())
                    .width(5f);
            Polyline polyline = mAmazonMap.addPolyline(opt);
            mPolyLines.add(polyline);
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
                Log.e("json", jsone.getMessage() + "/n" + message);
            }

            //Populate the map with pins for each event
            populateMarkers();
            mSelectedEvent = mFamilyMap.getSelectedEvent();
            if (mSelectedEvent != null) {
                mSelectedMarker = getMarkerFromEvent();
                mAmazonMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(mSelectedMarker.getPosition(),
                                6.0f));
                Person eventPerson = mFamilyMap.getPersonFromEvent(mSelectedEvent);
                mFamilyMap.setCurrentPerson(eventPerson);
                updateUI(eventPerson);
                drawMapLines();
            }

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
                    if(current.has("eventID")) {
                        event.setEventId(current.getString("eventID"));
                    } else continue;
                    if(current.has("personID")) {
                        event.setPersonId(current.getString("personID"));
                    } else continue;
                    if(current.has("latitude")) {
                        event.setLatitude(Float.parseFloat(current.getString("latitude")));
                    } else continue;
                    if(current.has("longitude")) {
                        event.setLongitude(Float.parseFloat(current.getString("longitude")));
                    } else continue;
                    if(current.has("country")) {
                        event.setCountry(current.getString("country"));
                    } else continue;
                    if(current.has("city")) {
                        event.setCity(current.getString("city"));
                    } else continue;
                    if(current.has("description")) {
                        event.setDescription(current.getString("description"));
                        if(!mFamilyMap.getEventsEnabled().keySet().contains(event.getDescription())){
                            mFamilyMap.getEventsEnabled().put(event.getDescription(), true);
                        }
                    } else continue;
                    if(current.has("year")) {
                        event.setYear(current.getString("year"));
                    } else continue;
                    if(current.has("descendant")) {
                        event.setDescendant(current.getString("descendant"));
                    } else continue;

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


            ((MainActivity)getActivity()).getMenuItemFilter().setEnabled(true);
            ((MainActivity)getActivity()).getMenuItemSearch().setEnabled(true);
            ((MainActivity)getActivity()).getMenuItemSettings().setEnabled(true);
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
