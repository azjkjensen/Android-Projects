package info.jkjensen.familymap.Model;

/**
 * Created by Jk on 3/30/2016.
 */
public class Person {
    String mDescendant;
    String mPersonID;
    String mFirstName;
    String mLastName;
    String mGender;
    String mSpouseID;

    public String getDescendant() {
        return mDescendant;
    }

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
}
