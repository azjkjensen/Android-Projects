package edu.byu.cs.superasteroids.database;

import android.database.sqlite.SQLiteDatabase;

import edu.byu.cs.superasteroids.model.MainBody;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class MainBodyDAO {

    private SQLiteDatabase db;

    public MainBodyDAO(SQLiteDatabase db) {
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
     * Takes <code>mainBody</code> and inserts it into the proper table
     * @param mainBody
     */
    public void addItem(MainBody mainBody){

    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }
}