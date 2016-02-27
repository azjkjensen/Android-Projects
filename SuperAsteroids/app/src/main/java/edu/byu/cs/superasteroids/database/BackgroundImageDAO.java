package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.BackgroundImage;
import edu.byu.cs.superasteroids.model.ViewableObject;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database table "objects"
 */
public class BackgroundImageDAO {

    private SQLiteDatabase db;

    private static BackgroundImageDAO instance = null;

    private BackgroundImageDAO() {}

    public static BackgroundImageDAO getInstance() {
        if(instance == null) {
            instance = new BackgroundImageDAO();
        }
        return instance;
    }
    /**
     * Sets the DAO database to database
     * @param database
     */
    public void setDatabase(SQLiteDatabase database){
//        Log.i("DAO", "Assigning database for BackgroundImageDAO");
        db = database;
    }

    /**
     * Takes <code>backgroundImage</code> and inserts it into the proper table
     * @param backgroundImagePath
     */
    public void addBackgroundImage(String backgroundImagePath){
        ContentValues values = new ContentValues();
//        values.put("position", backgroundImage.getPositionString());
//        values.put("scale", Float.toString(backgroundImage.getScale()));
//        values.put("objectId", Integer.toString(backgroundImage.getObjectID()));
        values.put("filePath", backgroundImagePath);
        db.insert("objects", null, values);
    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }

    /**
     * Returns a set of all background images from the database
     * @return
     */
    public ArrayList<BackgroundImage> getAllImages(){

        //First get all information necessary from objects table
        final String SQLGet = "SELECT * FROM objects";

        ArrayList<BackgroundImage> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BackgroundImage backgroundImage = new BackgroundImage();

                backgroundImage.setObjectID(cursor.getInt(0));
                String imagePath = cursor.getString(1);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    Log.i("modelPopulate", "Failed to load image " + imagePath);
                    throw new Exception("BackgroundImage failed to populate");
                }

                backgroundImage.setImagePath(imagePath);
                backgroundImage.setImageID(imageID);
                result.add(backgroundImage);

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
