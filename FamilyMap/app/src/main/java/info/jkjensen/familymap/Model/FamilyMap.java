package info.jkjensen.familymap.Model;

/**
 * Created by Jk on 3/16/2016.
 */
public class FamilyMap {
    private static FamilyMap ourInstance = new FamilyMap();

    public static FamilyMap getInstance() {
        return ourInstance;
    }

    private FamilyMap() {
    }
}
