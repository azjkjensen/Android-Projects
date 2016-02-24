package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.model.BackgroundImage;

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
    public Set<BackgroundImage> getAll(){
        return new HashSet<>();
    }
}
