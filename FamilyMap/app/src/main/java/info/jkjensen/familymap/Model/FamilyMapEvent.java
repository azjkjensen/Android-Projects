package info.jkjensen.familymap.Model;

/**
 * Created by Jk on 3/30/2016.
 */
public class FamilyMapEvent implements Comparable{
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

    @Override
    public int compareTo(Object another) {
        FamilyMapEvent fme = (FamilyMapEvent) another;
        if(this.mYear == null && fme.getYear() != null){
            return -1;
        } else if(fme.getYear() == null && this.mYear != null){
            return 1;
        } else if(fme.getYear() == null && this.mYear == null){
            //Compare descriptions alphabetically
            return this.mYear.toLowerCase().compareTo(fme.getYear().toLowerCase());
        } else { //If neither year is null

            if (Integer.parseInt(this.mYear) < Integer.parseInt(fme.getYear())) {
                return -1;
            } else if (Integer.parseInt(this.mYear) > Integer.parseInt(fme.getYear())) {
                return 1;
            } else return 0;
        }
    }
}
