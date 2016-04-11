package info.jkjensen.familymap.Model;

import android.graphics.Color;

import com.amazon.geo.mapsv2.AmazonMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Jk on 3/16/2016.
 */
public class FamilyMap {
    private static FamilyMap instance = null;

    /**Signifies that the user is logged in*/
    public boolean mIsUserLoggedIn = false;

    /**All user events in the model*/
    private ArrayList<FamilyMapEvent> mUserEvents;
    private ArrayList<FamilyMapEvent> mEventsCopyMASTER;

    /**Mapping of event desccriptions to colors */
    private HashMap<String, Float> mEventTypeColors;
    /**List of all possible event descriptions in the model*/
    private ArrayList<String> mEventTypes;
    /**List of all Person objects listed in the model*/
    private ArrayList<Person> mUserPersons;

    /*API access information*/
    private String mAuthToken;
    private String mUsername;
    private String mUserId;
    private String mUserFirstName;
    private String mUserLastName;
    private String mUserGender;
    private String mHostIP;
    private String mPort;
    private Person mCurrentPerson;

    /**Event that is selected on the map */
    private FamilyMapEvent mSelectedEvent = null;

    /*Signify whether to show each of the lines on the map*/
    private boolean showLifeStoryLines = true;
    private boolean showFamilyTreeLines = true;
    private boolean showSpouseLines = true;

    /**THe events that are enabled through filters*/
    private HashMap<String, Boolean> mEventsEnabled;

    /*Colors of each of the lines on the map*/
    private int mLifeStoryColor = Color.RED;
    private int mSpouseStoryColor = Color.BLUE;
    private int mFamilyTreeColor = Color.GREEN;

    /**The map layout object for displaying*/
    private AmazonMap mAmazonMap;

    /*Whether to show each of the required event filters*/
    private boolean mShowMaleEvents = true;
    private boolean mShowFemaleEvents = true;
    private boolean mShowMothersEvents = true;
    private boolean mShowFathersEvents = true;

    /**Gets the singleton object of our model*/
    public static FamilyMap getInstance() {
        if(instance == null){
            instance = new FamilyMap();
        }
        return instance;
    }

    private FamilyMap() {
        mEventsEnabled = new HashMap<>();
        mEventsCopyMASTER = new ArrayList<>();
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

    /**
     * Sets the events to the array given, and stores a list of all possible desccriptions.
     * @param userEvents the array to set mUserEvents to.
     */
    public void setUserEvents(ArrayList<FamilyMapEvent> userEvents) {
        mUserEvents = userEvents;
        mEventsCopyMASTER.addAll(mUserEvents);
        mEventTypeColors = new HashMap<>();
        setEventTypes();
    }

    public ArrayList<FamilyMapEvent> getEventsCopyMASTER() {
        return mEventsCopyMASTER;
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

    public HashMap<String, Float> getEventTypeColors() {
        return mEventTypeColors;
    }

    /**
     * Takes all events in the model, and creates a list of possible descriptions
     */
    private void setEventTypes() {
        mEventTypes = new ArrayList<>();
        mEventTypeColors = new HashMap<>();

        for(int i = 0; i < mUserEvents.size(); i++){
            FamilyMapEvent e = mUserEvents.get(i);
            if(!mEventTypes.contains(e.getDescription())){
                mEventTypes.add(e.getDescription());
            }
        }

        for(int j = 0; j < mEventTypes.size(); j++){
            float hue = j * (360f / mEventTypes.size());
            mEventTypeColors.put(mEventTypes.get(j), hue);
        }
    }

    public FamilyMapEvent getSelectedEvent() {
        return mSelectedEvent;
    }

    public void setSelectedEvent(FamilyMapEvent selectedEvent) {
        mSelectedEvent = selectedEvent;
    }

    public void resetSelectedEvent(){
        mSelectedEvent = null;
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

    /**
     * Gets all children of the person with the given id
     * @param parentID the id of the person whose children to find
     * @return a list of person objects for all children of the given person.
     */
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

    /**
     * Gets the person from the model with the given id.
     * @param personId the id of the person to get
     * @return
     */
    public Person getPersonByID(String personId) {
        for(Person p : mUserPersons){
            if(p.getPersonID().equals(personId)){
                return p;
            }
        }
        return null;
    }

    /**
     * Gets all events from the model with the given user id
     * @param userID the id of the person whose events to get
     * @return a list of FamilyMapEvent objects containing all person life events
     */
    public ArrayList<Object> getLifeEvents(String userID){
        ArrayList<FamilyMapEvent> intermediateResult = new ArrayList<>();
        ArrayList<Object> result = new ArrayList<>();

        for(FamilyMapEvent e : mUserEvents){
            if (e.getPersonId().equals(userID)){
                intermediateResult.add(e);
            }
        }
        result = reorderEventsForPersonActivity(intermediateResult);
        return result;
    }

    /**
     * Reorders the given list by specification
     * @param input list to sort
     * @return sorted list
     */
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

    /**
     * Gets the current person from the model, or gets the user if there is no given person
     * @return person
     */
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

    /**
     * Resets the current person to the user's information
     */
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

    /**
     * Gets the spouse id of the person with the given user id
     * @param userID the id of the person whose spouse to get
     * @return the id of the person's spouse
     */
    public String getSpouseID(String userID) {
        if(mUserPersons == null) return null;
        for(Person p : mUserPersons){
            if(p.getSpouseID().equals(userID)){
                return p.getPersonID();
            }
        }
        return null;
    }

    /**
     * Gets the person with the id associated with the given event.
     * @param event the event to find the person from
     * @return the Person the event belongs to
     */
    public Person getPersonFromEvent(FamilyMapEvent event){
        for(Person p : mUserPersons){
            if(p.getPersonID().equals(event.getPersonId())){
                return p;
            }
        }
        return null;
    }

    /**
     * Gets the color of the marker for the given event
     * @param event for which to get the color
     * @return the color for the event
     */
    public float getEventColor(FamilyMapEvent event) {
        return mEventTypeColors.get(event.getDescription());
    }

    public boolean showLifeStoryLines() {
        return showLifeStoryLines;
    }

    public void setShowLifeStoryLines(boolean showLifeStoryLines) {
        this.showLifeStoryLines = showLifeStoryLines;
    }

    public boolean showFamilyTreeLines() {
        return showFamilyTreeLines;
    }

    public void setShowFamilyTreeLines(boolean showFamilyTreeLines) {
        this.showFamilyTreeLines = showFamilyTreeLines;
    }

    public boolean showSpouseLines() {
        return showSpouseLines;
    }

    public void setShowSpouseLines(boolean showSpouseLines) {
        this.showSpouseLines = showSpouseLines;
    }

    public int getLifeStoryColor() {
        return mLifeStoryColor;
    }

    public void setLifeStoryColor(String lifeStoryColor) {
        if(lifeStoryColor.toLowerCase().equals("red")){
            mLifeStoryColor = Color.RED;
        } else if (lifeStoryColor.toLowerCase().equals("green")) {
            mLifeStoryColor = Color.GREEN;
        } else if(lifeStoryColor.toLowerCase().equals("blue")) {
            mLifeStoryColor = Color.BLUE;
        } else{
            mLifeStoryColor = Color.YELLOW;
        }
    }

    public int getSpouseStoryColor() {
        return mSpouseStoryColor;
    }

    public void setSpouseStoryColor(String spouseStoryColor) {
        if(spouseStoryColor.toLowerCase().equals("red")){
            mSpouseStoryColor = Color.RED;
        } else if (spouseStoryColor.toLowerCase().equals("green")) {
            mSpouseStoryColor = Color.GREEN;
        } else if(spouseStoryColor.toLowerCase().equals("blue")) {
            mSpouseStoryColor = Color.BLUE;
        } else{
            mSpouseStoryColor = Color.YELLOW;
        }
    }

    public int getFamilyTreeColor(){
        return mFamilyTreeColor;
    }

    /**
     * Set the family tree color based on the string/
     * @param familyTreeColor the string name of the color
     */
    public void setFamilyTreeColor(String familyTreeColor) {
        if(familyTreeColor.toLowerCase().equals("red")){
            mFamilyTreeColor = Color.RED;
        } else if (familyTreeColor.toLowerCase().equals("green")) {
            mFamilyTreeColor = Color.GREEN;
        } else if(familyTreeColor.toLowerCase().equals("blue")) {
            mFamilyTreeColor = Color.BLUE;
        } else{
            mFamilyTreeColor = Color.YELLOW;
        }
    }

    /**
     * Gets the earliest event for the given person
     * @param person the person the event belongs to
     * @return the earliest event for the person
     */
    public FamilyMapEvent getEarliestEvent(Person person) {
        FamilyMapEvent earliest = null;
        for(FamilyMapEvent e : mUserEvents){
            if(e.getPersonId().equals(person.getPersonID())){
                if(earliest == null) earliest = e;
                if(Integer.parseInt(e.getYear()) < Integer.parseInt(earliest.getYear())){
                    if(e.getDescription().toLowerCase().equals("birth")){
                        return e;
                    } else {
                        earliest = e;
                    }
                }
            }
        }
        return earliest;
    }

    /**
     * Gets all ancestors' events for the person with the given id
     * @param rootID the id of the person whose ancestors to find
     * @return list of ancestors' events
     */
    public ArrayList<FamilyMapEvent> getAncestors(String rootID) {
        Person root = getPersonByID(rootID);
        ArrayList<FamilyMapEvent> result = new ArrayList<>();

        return getAncestors(root, result);
    }

    /**
     * Recursive part of ancestor getting.
     * @param currentPerson current person we are on in the tree
     * @param result the current list of ancestors
     * @return the result once we have hit a base case.
     */
    private ArrayList<FamilyMapEvent> getAncestors(Person currentPerson, ArrayList<FamilyMapEvent> result){
        FamilyMapEvent earliestEventForPerson = getEarliestEvent(currentPerson);
        if(earliestEventForPerson != null) {
            result.add(earliestEventForPerson);
        }

        if(currentPerson.hasFather()){
            Person personFather = getPersonByID(currentPerson.getFatherID());
            getAncestors(personFather, result);
        }

        if(currentPerson.hasMother()){
            Person personMother = getPersonByID(currentPerson.getMotherID());
            getAncestors(personMother, result);
        }

        return result;
    }

    /**
     * Gets the birth event associated with the given person if it exists.
     * @param person the person to get the event from
     * @return the birth event, or null if it doesn't exist.
     */
    public FamilyMapEvent getBirthEvent(Person person) {
        for(FamilyMapEvent event : mUserEvents){
            if(event.getPersonId().equals(person.getPersonID()) &&
                    event.getDescription().toLowerCase().equals("birth")){
                return event;
            }
        }
        return null;
    }

    /**
     * Resets singleton for a new user session.
     */
    public void resetUserSession() {
        instance = null;
    }

    public void setAmazonMap(AmazonMap amazonMap) {
        mAmazonMap = amazonMap;
    }

    public AmazonMap getAmazonMap() {
        return mAmazonMap;
    }

    public HashMap<String, Boolean> getEventsEnabled() {
        return mEventsEnabled;
    }

    public boolean showMaleEvents() {
        return mShowMaleEvents;
    }

    public boolean showFemaleEvents() {
        return mShowFemaleEvents;
    }

    public boolean showMothersEvents() {
        return mShowMothersEvents;
    }

    public boolean showFathersEvents() {
        return mShowFathersEvents;
    }

    public void setShowMaleEvents(boolean showMaleEvents) {
        mShowMaleEvents = showMaleEvents;
    }

    public void setShowFemaleEvents(boolean showFemaleEvents) {
        mShowFemaleEvents = showFemaleEvents;
    }

    public void setShowMothersEvents(boolean showMothersEvents) {
        mShowMothersEvents = showMothersEvents;
    }

    public void setShowFathersEvents(boolean showFathersEvents) {
        mShowFathersEvents = showFathersEvents;
    }
}
