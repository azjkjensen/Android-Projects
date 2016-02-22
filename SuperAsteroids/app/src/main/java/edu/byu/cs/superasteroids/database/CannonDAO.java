package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.model.Cannon;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class CannonDAO {

    private SQLiteDatabase db;

    private static CannonDAO instance = null;

    public CannonDAO() {}

    public static CannonDAO getInstance() {
        if(instance == null) {
            instance = new CannonDAO();
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
     * Takes <code>cannon</code> and inserts it into the proper table
     * @param cannon
     */
    public void addCannon(Cannon cannon){
        ContentValues values = new ContentValues();
        values.put("attachPoint", cannon.getAttachPoint().toString());
        values.put("emitPoint", cannon.getEmitPoint().toString());
        values.put("attackImage", cannon.getLaserShot().getAttackViewableInfo().getImage());
        values.put("attackImageHeight", cannon.getLaserShot().getAttackViewableInfo().getImageHeight());
        values.put("attackImageWidth", cannon.getLaserShot().getAttackViewableInfo().getImageWidth());
        values.put("attackSound", cannon.getLaserShot().getAttackSound());
        values.put("damage", cannon.getLaserShot().getDamage());
        values.put("image", cannon.getMainViewableInfo().getImage());
        values.put("imageWidth", cannon.getMainViewableInfo().getImageWidth());
        values.put("imageHeight", cannon.getMainViewableInfo().getImageHeight());
        long result = db.insert("cannons", null, values);
//        if(result == -1) Log.i("JsonDomParserExample", "Failed to add cannon to db.");
//        else Log.i("JsonDomParserExample", "Successfully added cannon to db");
    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }

    /**
     * Returns a set of all Cannons from the database
     * @return
     */
    public Set<Cannon> getAll(){
        return new HashSet<>();
    }
}
