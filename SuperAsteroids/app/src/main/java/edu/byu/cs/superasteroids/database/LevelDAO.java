package edu.byu.cs.superasteroids.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jk on 2/12/2016.
 */
public class LevelDAO {
    private SQLiteDatabase db;

    public LevelDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Sets the DAO database to param:database
     * @param database
     */
    public void setDatabase(SQLiteDatabase database){
        db = database;
    }

    public void addItem(){

    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }
}
