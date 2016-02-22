package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.model.ExtraPart;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class ExtraPartDAO {

    private SQLiteDatabase db;

    private static ExtraPartDAO instance = null;

    public ExtraPartDAO() {}

    public static ExtraPartDAO getInstance() {
        if(instance == null) {
            instance = new ExtraPartDAO();
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
     * Takes <code>extraPart</code> and inserts it into the proper table
     * @param extraPart
     */
    public void addExtraPart(ExtraPart extraPart){
        ContentValues values = new ContentValues();
        values.put("attachPoint", extraPart.getAttachPoint().toString());
        values.put("image", extraPart.getViewableInfo().getImage());
        values.put("imageWidth", extraPart.getViewableInfo().getImageWidth());
        values.put("imageHeight", extraPart.getViewableInfo().getImageHeight());
        long result = db.insert("extraParts", null, values);
//        if(result == -1) Log.i("JsonDomParserExample", "Failed to add extraPart to db.");
//        else Log.i("JsonDomParserExample", "Successfully added extraPart to db");
    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }

    /**
     * Returns a set of all Extra Parts from the database
     * @return
     */
    public Set<ExtraPart> getAll(){
        return new HashSet<>();
    }
}

