package info.jkjensen.familymap.Model;

/**
 * Created by Jk on 3/30/2016.
 */
public class FamilyMapEvent {
    private String mEventId;
    private String mPersonId;
    private float mLatitude;
    private float mLongitude;
    private String mCountry;
    private String mCity;
    private String mDescription;
    private String mYear;
    private String mDescendant;

    public String getEventId() {
        return mEventId;
    }

    public void setEventId(String eventId) {
        mEventId = eventId;
    }

    public String getPersonId() {
        return mPersonId;
    }

    public void setPersonId(String personId) {
        mPersonId = personId;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public void setLatitude(float latitude) {
        mLatitude = latitude;
    }

    public float getLongitude() {
        return mLongitude;
    }

    public void setLongitude(float longitude) {
        mLongitude = longitude;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getDescendant() {
        return mDescendant;
    }

    public void setDescendant(String descendant) {
        mDescendant = descendant;
    }
}
