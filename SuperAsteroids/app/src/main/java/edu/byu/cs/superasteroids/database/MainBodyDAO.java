package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.ViewableObject;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class MainBodyDAO {

    private SQLiteDatabase db;

    private static MainBodyDAO instance = null;

    public MainBodyDAO() {}

    public static MainBodyDAO getInstance(){
        if(instance == null) instance = new MainBodyDAO();
        return instance;
    }

    /**
     * Sets the DAO database to param:database
     * @param database
     */
    public void setDatabase(SQLiteDatabase database){
        db = database;
    }

    /**
     * Takes <code>mainBody</code> and inserts it into the proper table
     * @param mainBody
     */
    public void addMainBody(MainBody mainBody){
        ContentValues values = new ContentValues();
        values.put("cannonAttach", mainBody.getCannonAttach().toString());
        values.put("engineAttach", mainBody.getEngineAttach().toString());
        values.put("extraAttach", mainBody.getExtraAttach().toString());
        values.put("image", mainBody.getViewableInfo().getImage());
        values.put("imageHeight", mainBody.getViewableInfo().getImageHeight());
        values.put("imageWidth", mainBody.getViewableInfo().getImageWidth());
        long result = db.insert("mainBodies", null, values);
//        if(result == -1) Log.i("JsonDomParserExample", "Failed to add Main Body to db.");
//        else Log.i("JsonDomParserExample", "Successfully added Main Body to db");
    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }

    /**
     * Returns a set of all Main Bodies from the database
     * @return
     */
    public Set<MainBody> getAll(){
        final String SQLGet = "SELECT * FROM mainBodies";

        Set<MainBody> result = new HashSet<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MainBody mainBody = new MainBody();

                mainBody.setCannonAttach(new Coordinate(cursor.getString(1)));
                mainBody.setEngineAttach(new Coordinate(cursor.getString(2)));
                mainBody.setExtraAttach(new Coordinate(cursor.getString(3)));

                String imagePath = cursor.getString(4);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    Log.i("modelPopulate", "Failed to load image " + imagePath);
                    throw new Exception("MainBody failed to populate");
                }

                mainBody.setViewableInfo(
                        new ViewableObject(
                                imagePath,
                                cursor.getInt(5),
                                cursor.getInt(6),
                                imageID
                        ));

                result.add(mainBody);

                cursor.moveToNext();
            }
        } catch(Exception e) {
            Log.i("modelPopulate", e.getMessage());
        } finally {
            cursor.close();
        }

        return result;
    }
}
