package edu.byu.cs.superasteroids.database;

import android.database.sqlite.SQLiteDatabase;

import edu.byu.cs.superasteroids.model.ExtraPart;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class ExtraPartDAO {

    private SQLiteDatabase db;

    public ExtraPartDAO(SQLiteDatabase db) {
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
     * Takes <code>extraPart</code> and inserts it into the proper table
     * @param extraPart
     */
    public void addItem(ExtraPart extraPart){

    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }
}

