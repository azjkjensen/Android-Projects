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


    public boolean mIsUserLoggedIn = false;

    private ArrayList<FamilyMapEvent> mUserEvents;
    private ArrayList<FamilyMapEvent> mEventsCopyMASTER;

    private HashMap<String, Float> mEventTypeColors;
    private ArrayList<String> mEventTypes;
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
    private FamilyMapEvent mSelectedEvent = null;

    private boolean showLifeStoryLines = true;
    private boolean showFamilyTreeLines = true;
    private boolean showSpouseLines = true;

    private HashMap<String, Boolean> mEventsEnabled;

    private int mLifeStoryColor = Color.RED;
    private int mSpouseStoryColor = Color.BLUE;
    private int mFamilyTreeColor = Color.GREEN;
    private AmazonMap mAmazonMap;

    private boolean mShowMaleEvents = true;
    private boolean mShowFemaleEvents = true;
    private boolean mShowMothersEvents = true;
    private boolean mShowFathersEvents = true;

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

    public void setUserEvents(ArrayList<FamilyMapEvent> userEvents) {
        mUserEvents = userEvents;
        mEventsCopyMASTER.addAll(mUserEvents);
        mEventTypeColors = new HashMap<>();
        setEventTypes();
    }

    public ArrayList<FamilyMapEvent> getEventsCopyMASTER() {
        return mEventsCopyMASTER;
    }

    public void setShowingEvents() {
        //Change to a set then back
        if(mShowMaleEvents){

        }
        if(mShowFemaleEvents){

        }
        for(String eventName : mEventsEnabled.keySet()){
            FamilyMapEvent event = null;
            for(FamilyMapEvent e : mUserEvents){
                if(eventName.equals(e.getDescription())){
                    event = e;
                    break;
                }
            }
            if(mEventsEnabled.get(eventName) && event != null){
                mEventsCopyMASTER.add(event);
            }
        }
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

    public Person getPersonByID(String parentID) {
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

    public String getSpouseID(String userID) {
        if(mUserPersons == null) return null;
        for(Person p : mUserPersons){
            if(p.getSpouseID().equals(userID)){
                return p.getPersonID();
            }
        }
        return null;
    }

    public Person getPersonFromEvent(FamilyMapEvent event){
        for(Person p : mUserPersons){
            if(p.getPersonID().equals(event.getPersonId())){
                return p;
            }
        }
        return null;
    }

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

    public ArrayList<FamilyMapEvent> getAncestors(String rootID) {
        Person root = getPersonByID(rootID);
        ArrayList<FamilyMapEvent> result = new ArrayList<>();

        return getAncestors(root, result);
    }

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

    public FamilyMapEvent getBirthEvent(Person person) {
        for(FamilyMapEvent event : mUserEvents){
            if(event.getPersonId().equals(person.getPersonID()) &&
                    event.getDescription().toLowerCase().equals("birth")){
                return event;
            }
        }
        return null;
    }

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

    public void syncFilters() {
        //Copy all original events into mUserEvents
        mUserEvents.clear();
        mUserEvents.addAll(mEventsCopyMASTER);
        //Create all
        ArrayList<FamilyMapEvent> eventsLengthCopy = new ArrayList<>();
        eventsLengthCopy.clear();
        eventsLengthCopy.addAll(mUserEvents);

        if(!mShowMaleEvents){
            for(int i = 0; i < eventsLengthCopy.size(); i++){
                FamilyMapEvent event = eventsLengthCopy.get(i);
                if(getPersonFromEvent(event).getGender().toLowerCase().equals("m")){
                    mUserEvents.remove(event);
                }
            }
        }

        eventsLengthCopy.clear();
        eventsLengthCopy.addAll(mUserEvents);
        if(!mShowFemaleEvents){
            for(int i = 0; i < eventsLengthCopy.size(); i++){
                FamilyMapEvent event = eventsLengthCopy.get(i);
                if(getPersonFromEvent(event).getGender().toLowerCase().equals("f")){
                    mUserEvents.remove(event);
                }
            }
        }

        if(!mShowFathersEvents){

        }
        if(!mShowMothersEvents){

        }

        eventsLengthCopy.clear();
        eventsLengthCopy.addAll(mUserEvents);
        for(String eventName : mEventsEnabled.keySet()){
            boolean isEventshown = mEventsEnabled.get(eventName);

            if(!isEventshown){
                //Get the event by name
                for(int i = 0; i < eventsLengthCopy.size(); i++){
                    FamilyMapEvent e = eventsLengthCopy.get(i);
                    if(e.getDescription().equals(eventName)){
                        mUserEvents.remove(e);
                    }
                }
            }
        }
    }
}
