package info.jkjensen.familymap.Model;

/**
 * Created by Jk on 3/30/2016.
 * This class represents a person with their given information.
 */
public class Person {
    String mDescendant;
    String mPersonID;
    String mFirstName;
    String mLastName;
    String mGender;
    String mSpouseID = null;
    private String mFatherID = null;
    private String mMotherID = null;
    private String mRelationship = null;

    public void setDescendant(String descendant) {
        mDescendant = descendant;
    }

    public String getPersonID() {
        return mPersonID;
    }

    public void setPersonID(String personID) {
        mPersonID = personID;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getSpouseID() {
        return mSpouseID;
    }

    public void setSpouseID(String spouseID) {
        mSpouseID = spouseID;
    }

    public void setFatherID(String fatherID) {
        mFatherID = fatherID;
    }

    public String getFatherID() {
        return mFatherID;
    }

    public void setMotherID(String motherID) {
        mMotherID = motherID;
    }

    public String getMotherID() {
        return mMotherID;
    }

    public String getRelationship() {
        return mRelationship;
    }

    public void setRelationship(String relationship) {
        mRelationship = relationship;
    }

    public boolean hasFather() {
        return mFatherID != null;
    }

    public boolean hasMother() {
        return mMotherID != null;
    }
}
