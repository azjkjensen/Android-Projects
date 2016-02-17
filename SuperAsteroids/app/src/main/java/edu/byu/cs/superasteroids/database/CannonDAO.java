package edu.byu.cs.superasteroids.database;

import android.database.sqlite.SQLiteDatabase;

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

    public CannonDAO(SQLiteDatabase db) {
        this.db = db;
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
    public void addItem(Cannon cannon){

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
