package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.model.PowerCore;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class PowerCoreDAO {

    private SQLiteDatabase db;

    private static PowerCoreDAO instance = null;

    public PowerCoreDAO() {}


    public static PowerCoreDAO getInstance() {
        if (instance == null) {
            instance = new PowerCoreDAO();
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
     * Takes <code>powerCore</code> and inserts it into the proper table
     * @param powerCore
     */
    public void addPowerCore (PowerCore powerCore){
        ContentValues values = new ContentValues();
        values.put("image", powerCore.getImage());
        values.put("cannonBoost", powerCore.getCannonBoost());
        values.put("engineBoost", powerCore.getEngineBoost());
        long result = db.insert("powerCores", null, values);
        if(result == -1) Log.i("JsonDomParserExample", "Failed to add powerCore to db.");
        else Log.i("JsonDomParserExample", "Successfully added powerCore to db");
    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }

    /**
     * Returns a set of all Power Cores from the database
     * @return
     */
    public Set<PowerCore> getAll(){
        return new HashSet<>();
    }
}
