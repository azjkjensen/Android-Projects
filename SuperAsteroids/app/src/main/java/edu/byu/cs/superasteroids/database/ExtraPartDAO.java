package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.ViewableObject;

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
    public ArrayList<ExtraPart> getAll(){

        final String SQLGet = "SELECT * FROM extraParts";

        ArrayList<ExtraPart> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ExtraPart extraPart = new ExtraPart();

                extraPart.setAttachPoint(new Coordinate(cursor.getString(1)));

                String imagePath = cursor.getString(2);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    Log.i("modelPopulate", "Failed to load image " + imagePath);
                    throw new Exception("ExtraPart failed to populate");
                }

                extraPart.setViewableInfo(
                        new ViewableObject(
                                imagePath,
                                cursor.getInt(3),
                                cursor.getInt(4),
                                imageID
                        ));

                result.add(extraPart);

                cursor.moveToNext();
            }
        } catch(Exception e) {
            Log.i("modelPopulate", e.getMessage());
        } finally {
            cursor.close();
        }

        return result;
    }
}

