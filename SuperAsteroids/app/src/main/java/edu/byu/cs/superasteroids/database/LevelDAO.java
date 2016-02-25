package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.BackgroundImage;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.Level;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class LevelDAO {
    private SQLiteDatabase db;

    private static LevelDAO instance;

    public LevelDAO() {}

    public static LevelDAO getInstance() {
        if(instance == null) {
            instance = new LevelDAO();
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
     * Takes <code>level</code> and inserts it into the proper table
     * @param level
     */
    public void addLevel(Level level){
        ContentValues values = new ContentValues();
        values.put("number", level.getNumber());
        values.put("title", level.getTitle());
        values.put("hint", level.getHint());
        values.put("width", level.getWidth());
        values.put("height", level.getHeight());
        values.put("music", level.getMusic());

        long result = db.insert("levels", null, values);
//        if(result == -1) Log.i("JsonDomParserExample", "Failed to add level to db.");
//        else Log.i("JsonDomParserExample", "Successfully added level to db");
    }

    public void addLevelAsteroid(int number, int asteroidId, int levelNumber){
        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("asteroidId", asteroidId);
        values.put("levelNumber", levelNumber);

        long result = db.insert("levelAsteroids", null, values);
//        if(result == -1) Log.i("JsonDomParserExample", "Failed to add levelAsteroid to db.");
//        else Log.i("JsonDomParserExample", "Successfully added levelAsteroid to db");
    }

    public void addLevelObject(Coordinate position, int objectId, float scale, int levelNumber){
        ContentValues values = new ContentValues();
        values.put("position", position.toString());
        values.put("objectId", Integer.toString(objectId));
        values.put("scale", Float.toString(scale));
        values.put("levelNumber", levelNumber);

        long result = db.insert("levelObjects", null, values);
//        if(result == -1) Log.i("JsonDomParserExample", "Failed to add levelObject to db.");
//        else Log.i("JsonDomParserExample", "Successfully added levelObject to db");
    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }

    /**
     * Returns a set of all Levels from the database
     * gets the basic level information for each level, and grabs the level objects and level
     * asteroids on the way.
     * @return
     */
    public Set<Level> getAll(Set<AsteroidType> asteroidTypes, Set<BackgroundImage> backgroundImages){

        final String SQLGet = "SELECT * FROM levels";

        Set<Level> result = new HashSet<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Level level = new Level();

                int levelNumber = cursor.getInt(0);
                level.setNumber(levelNumber);
                level.setTitle(cursor.getString(1));
                level.setHint(cursor.getString(2));
                level.setWidth(cursor.getInt(3));
                level.setHeight(cursor.getInt(4));
                String soundPath = cursor.getString(5);
                int soundID = ContentManager.getInstance().loadSound(soundPath);

                result.add(level);

                cursor.moveToNext();
            }
        } catch(Exception e) {
            Log.i("modelPopulate", e.getMessage());
        } finally {
            cursor.close();
        }

        //Pull levelAsteroids
        setLevelAsteroids(result, asteroidTypes);
        //Pull levelObjects
        setLevelObjects(result, backgroundImages);

        return result;
    }

    private void setLevelAsteroids(Set<Level> levels, Set<AsteroidType> asteroidTypes){

        final String SQLGet = "SELECT * FROM levelAsteroids";

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                int numberOfAsteroidOnLevel = cursor.getInt(0);
                int asteroidID = cursor.getInt(1);
                int levelNumber = cursor.getInt(2);

                for(Level l : levels) {
                    if(l.getNumber() == levelNumber) {
                        for (AsteroidType current : asteroidTypes) {
                            if (current.getID() == asteroidID) {
                                l.getLevelAsteroids().put(current, numberOfAsteroidOnLevel);
                                break;
                            }
                        }
                        break;
                    }
                }

                cursor.moveToNext();
            }
        } catch(Exception e) {
            Log.i("modelPopulate", e.getMessage());
        } finally {
            cursor.close();
        }
    }

    private void setLevelObjects(Set<Level> levels, Set<BackgroundImage> backgroundImages){

        final String SQLGet = "SELECT * FROM levelObjects";

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                Coordinate position = new Coordinate(cursor.getString(0));
                int objectID = cursor.getInt(1);
                float scale = cursor.getFloat(2);
                int levelNumber = cursor.getInt(3);

                for(Level l : levels) {
                    if(l.getNumber() == levelNumber) {
                        for (BackgroundImage current : backgroundImages) {
                            if (current.getObjectID() == objectID) {
                                current.setPosition(position);
                                current.setScale(scale);
                                l.getBackgroundImages().add(current);
                                break;
                            }
                        }
                        break;
                    }
                }

                cursor.moveToNext();
            }
        } catch(Exception e) {
            Log.i("modelPopulate", e.getMessage());
        } finally {
            cursor.close();
        }
    }
}
