package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

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
     * @return
     */
    public Set<Level> getAll(){
        return new HashSet<>();
    }
}
