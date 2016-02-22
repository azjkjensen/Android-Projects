package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.model.Engine;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class EngineDAO {

    private SQLiteDatabase db;

    private static EngineDAO instance = null;

    public EngineDAO() {}


    public static EngineDAO getInstance() {
        if(instance == null) {
            instance = new EngineDAO();
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
     * Takes <code>engine</code> and inserts it into the proper table
     * @param engine
     */
    public void addEngine (Engine engine){
        ContentValues values = new ContentValues();
        values.put("baseSpeed", engine.getBaseSpeed());
        values.put("baseTurnRate", engine.getBaseTurnRate());
        values.put("attachPoint", engine.getAttachPoint().toString());
        values.put("image", engine.getViewableInfo().getImage());
        values.put("imageWidth", engine.getViewableInfo().getImageWidth());
        values.put("imageHeight", engine.getViewableInfo().getImageHeight());
        long result = db.insert("engines", null, values);
        if(result == -1) Log.i("JsonDomParserExample", "Failed to add engine to db.");
        else Log.i("JsonDomParserExample", "Successfully added engine to db");
    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }

    /**
     * Returns a set of all Engines from the database
     * @return
     */
    public Set<Engine> getAll(){
        return new HashSet<>();
    }
}
