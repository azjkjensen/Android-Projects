package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.Laser;
import edu.byu.cs.superasteroids.model.ViewableObject;

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

        final String SQLGet = "SELECT * FROM cannons";

        Set<Cannon> result = new HashSet<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Cannon cannon = new Cannon();

                cannon.setAttachPoint(new Coordinate(cursor.getString(1)));
                cannon.setEmitPoint(new Coordinate(cursor.getString(2)));
                String attackImagePath = cursor.getString(3);
                int attackImageID = ContentManager.getInstance().loadImage(attackImagePath);
                if(attackImageID == -1) {
                    Log.i("modelPopulate", "Failed to load image " + attackImagePath);
                    throw new Exception("Cannon failed to populate");
                }

                int attackImageHeight = cursor.getInt(4);
                int attackImageWidth = cursor.getInt(5);
                String attackSoundPath = cursor.getString(6);
                ViewableObject attackViewable = new ViewableObject(attackImagePath,
                        attackImageWidth, attackImageHeight, attackImageID);
                int attackSoundID = ContentManager.getInstance().loadSound(attackSoundPath);
                int damage = cursor.getInt(7);
                cannon.setLaserShot(new Laser(attackViewable, attackSoundPath, attackSoundID, damage));

                String imagePath = cursor.getString(8);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    Log.i("modelPopulate", "Failed to load image " + imagePath);
                    throw new Exception("Cannon failed to populate");
                }
                int imageWidth= cursor.getInt(9);
                int imageHeight = cursor.getInt(10);

                cannon.setMainViewableInfo(
                        new ViewableObject(
                                imagePath,
                                imageWidth,
                                imageHeight,
                                imageID
                        ));
                result.add(cannon);

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
