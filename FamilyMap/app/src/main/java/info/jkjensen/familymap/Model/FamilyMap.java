package info.jkjensen.familymap.Model;

import com.amazon.geo.mapsv2.model.Marker;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jk on 3/16/2016.
 */
public class FamilyMap {
    private static FamilyMap instance = null;

    private Marker mSelectedMarker = null;

    public boolean mIsUserLoggedIn = false;

    private ArrayList<FamilyMapEvent> mUserEvents;
    private ArrayList<Person> mUserPersons;

    private String mAuthToken;
    private String mUsername;
    private String mUserId;
    private String mUserFirstName;
    private String mUserLastName;
    private String mUserGender;
    private String mHostIP;
    private String mPort;
    private Person mCurrentPerson;

    public static FamilyMap getInstance() {
        if(instance == null){
            instance = new FamilyMap();
        }
        return instance;
    }

    private FamilyMap() {
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        mAuthToken = authToken;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public void setUserFirstName(String userFirstName) {
        mUserFirstName = userFirstName;
    }

    public void setUserLastName(String userLastName) {
        mUserLastName = userLastName;
    }

    public void setUserGender(String userGender) {
        mUserGender = userGender;
    }

    public String getFullName(){
        return mUserFirstName + " " + mUserLastName;
    }

    public ArrayList<FamilyMapEvent> getUserEvents() {
        return mUserEvents;
    }

    public void setUserEvents(ArrayList<FamilyMapEvent> userEvents) {
        mUserEvents = userEvents;
    }

    public void setHostIP(String hostIP) {
        mHostIP = hostIP;
    }

    public String getHostIP(){
        return mHostIP;
    }

    public void setPort(String port) {
        mPort = port;
    }

    public String getPort(){
        return mPort;
    }

    public ArrayList<Person> getUserPersons() {
        return mUserPersons;
    }

    public void setUserPersons(ArrayList<Person> userPersons) {
        mUserPersons = userPersons;
    }

    public Marker getSelectedMarker() {
        return mSelectedMarker;
    }

    public void setSelectedMarker(Marker selectedMarker) {
        mSelectedMarker = selectedMarker;
    }

    /**
     * Gets a list of immediate family members of the given person p
     * @param p the person to retrieve family information for
     * @return a list of person objects for the family
     */
    public ArrayList<Object> getImmediateFamily(Person p){
        ArrayList<Object> result = new ArrayList<>();

        // Parents
        Person mother = getPersonByID(p.getMotherID());
        if(mother != null){
            mother.setRelationship("Mother");
            result.add(mother);
        }
        Person father = getPersonByID(p.getFatherID());
        if(father != null){
            father.setRelationship("Father");
            result.add(father);
        }
        // Spouse
        Person spouse = getPersonByID(p.getSpouseID());
        if(spouse != null){
            if(p.getGender().equals("m")){
                spouse.setRelationship("Wife");
            } else{
                spouse.setRelationship("Husband");
            }
            result.add(spouse);
        }
        // Children
        ArrayList<Object> children = getChildren(p.getPersonID());
        if(children.size() != 0) result.addAll(children);

        return result;
    }

    private ArrayList<Object> getChildren(String parentID) {
        ArrayList<Object> childrenResult = new ArrayList<>();

        for(Person p : mUserPersons){
            if(p.getFatherID() == null || p.getMotherID() == null) continue;
            if(p.getFatherID().equals(parentID) ||
                    p.getMotherID().equals(parentID)){
                p.setRelationship("Child");
                childrenResult.add(p);
            }
        }

        return childrenResult;
    }

    private Person getPersonByID(String parentID) {
        for(Person p : mUserPersons){
            if(p.getPersonID().equals(parentID)){
                return p;
            }
        }
        return null;
    }

    public ArrayList<Object> getLifeEvents(String userID){
        ArrayList<FamilyMapEvent> intermediateResult = new ArrayList<>();
        ArrayList<Object> result = new ArrayList<>();

        for(FamilyMapEvent e : mUserEvents){
            if (e.getPersonId().equals(userID)){
                intermediateResult.add(e);
            }
        }
        // TODO: Order events according to specification.
        result = reorderEventsForPersonActivity(intermediateResult);
        return result;
    }

    private ArrayList<Object> reorderEventsForPersonActivity(ArrayList<FamilyMapEvent> input) {
        ArrayList<Object> result = new ArrayList<>();
        ArrayList<FamilyMapEvent> inputCopy = new ArrayList<>(input.size() + 1);
        inputCopy.addAll(input);

        //Add birth events first
        for(FamilyMapEvent e : input){
            if(e.getDescription().toLowerCase().equals("birth")){
                result.add(e);
                inputCopy.remove(e);
            }
        }
        //Sort the remaining elements by year/description
        Collections.sort(inputCopy);

        //Get the death event if it exists and move it to the end of the list
        FamilyMapEvent deathEvent = null;

        for(FamilyMapEvent e : inputCopy){
            if(e.getDescription().toLowerCase().equals("death")){
                deathEvent = e;
            }
        }
        if(deathEvent != null){
            inputCopy.remove(deathEvent);
            inputCopy.add(deathEvent);
        }

        //Add all remaining elements (ordered) to the result/birth event
        result.addAll(inputCopy);

        return result;
    }

    public Person getCurrentPerson() {
        if(mCurrentPerson == null){
            mCurrentPerson = new Person();
            mCurrentPerson.setPersonID(mUserId);
            mCurrentPerson.setGender(mUserGender);
            mCurrentPerson.setFirstName(mUserFirstName);
            mCurrentPerson.setLastName(mUserLastName);
            mCurrentPerson.setSpouseID(getSpouseID(mUserId));
        }
        return mCurrentPerson;
    }

    public void resetCurrentPerson(){
        mCurrentPerson = new Person();
        mCurrentPerson.setPersonID(mUserId);
        mCurrentPerson.setGender(mUserGender);
        mCurrentPerson.setFirstName(mUserFirstName);
        mCurrentPerson.setLastName(mUserLastName);
    }

    public void setCurrentPerson(Person currentPerson) {
        mCurrentPerson = currentPerson;
    }

    // TODO: Make sure this method is functioning properly
    private String getSpouseID(String userID) {
        if(mUserPersons == null) return "error";
        for(Person p : mUserPersons){
            if(p.getSpouseID() == userID){
                return p.getSpouseID();
            }
        }
        return "error";
    }

    public Person getPersonFromEvent(FamilyMapEvent event){
        for(Person p : mUserPersons){
            if(p.getPersonID().equals(event.getPersonId())){
                return p;
            }
        }
        return null;
    }
}
