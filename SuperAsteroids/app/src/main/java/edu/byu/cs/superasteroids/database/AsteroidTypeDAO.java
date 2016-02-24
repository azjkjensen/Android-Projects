package edu.byu.cs.superasteroids.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.ViewableObject;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class AsteroidTypeDAO {

    private SQLiteDatabase db;

    private static AsteroidTypeDAO instance = null;

    private AsteroidTypeDAO() {
    }

    public static AsteroidTypeDAO getInstance() {
        if(instance == null) {
            instance = new AsteroidTypeDAO();
        }
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
     * Takes <code>asteroid</code> and inserts it into the proper table
     * @param asteroid
     */
    public void addAsteroidType(AsteroidType asteroid){
        ContentValues values = new ContentValues();
        values.put("name", asteroid.getName());
        values.put("type", asteroid.getType());
        values.put("image", asteroid.getViewableInfo().getImage());
        values.put("imageHeight", asteroid.getViewableInfo().getImageHeight());
        values.put("imageWidth", asteroid.getViewableInfo().getImageWidth());
        long result = db.insert("asteroidTypes", null, values);
//        if(result == -1) Log.i("JsonDomParserExample", "Failed to add Asteroid Type to db.");
    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public Set<AsteroidType> getByID(int id){
        final String SQLGet = "SELECT NAME, TYPE, IMAGE, IMAGE_HEIGHT," +
                "IMAGE_WIDTH FROM asteroidTypes WHERE ID = " + Integer.toString(id);

        Set<AsteroidType> result = new HashSet<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{Integer.toString(id)});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AsteroidType asteroidType = new AsteroidType();

                asteroidType.setID(cursor.getInt(0));
                asteroidType.setName(cursor.getString(1));
                asteroidType.setType(cursor.getString(2));
                String imagePath = cursor.getString(3);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) Log.i("modelPopulate", "Failed to load image " + imagePath);
                asteroidType.setImageID(imageID);
                asteroidType.setViewableInfo(
                        new ViewableObject(
                            imagePath,
                            cursor.getInt(4),
                            cursor.getInt(5)
                ));

                result.add(asteroidType);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }

    /**
     * Gets all AsteroidType items from the table.
     * @return the set of all AsteroidTypes from the table
     */
    public Set<AsteroidType> getAll(){
        final String SQLGet = "SELECT * FROM asteroidTypes";

        Set<AsteroidType> result = new HashSet<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AsteroidType asteroidType = new AsteroidType();

                asteroidType.setID(cursor.getInt(0));
                asteroidType.setName(cursor.getString(1));
                asteroidType.setType(cursor.getString(2));
                String imagePath = cursor.getString(3);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    Log.i("modelPopulate", "Failed to load image " + imagePath);
                    throw new Exception("AsteroidType failed to populate");
                }
                asteroidType.setImageID(imageID);
                asteroidType.setViewableInfo(
                        new ViewableObject(
                                imagePath,
                                cursor.getInt(4),
                                cursor.getInt(5)
                        ));

                result.add(asteroidType);

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