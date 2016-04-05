package info.jkjensen.familymap.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jk on 3/16/2016.
 */
public class FamilyMap {
    private static FamilyMap instance = null;

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

    /**
     * Gets a list of immediate family members of the given person p
     * @param p the person to retrieve family information for
     * @return a list of person objects for the family
     */
    public ArrayList<Object> getImmediateFamily(Person p){
        ArrayList<Object> result = new ArrayList<>();

        //Temporary
        for(Person person : mUserPersons){
            result.add(person);
        }

        return result;
    }

    public ArrayList<Object> getLifeEvents(String userID){
        ArrayList<Object> result = new ArrayList<>();

        for(FamilyMapEvent e : mUserEvents){
            if (e.getPersonId().equals(userID)){
                result.add(e);
            }
        }
        //TODO: Order events according to specification.

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

    public void setCurrentPerson(Person currentPerson) {
        mCurrentPerson = currentPerson;
    }

    // TODO: Make sure this method is functioning properly
    private String getSpouseID(String userID) {
        for(Person p : mUserPersons){
            if(p.getSpouseID() == userID){
                return p.getSpouseID();
            }
        }
        return "error";
    }
}
