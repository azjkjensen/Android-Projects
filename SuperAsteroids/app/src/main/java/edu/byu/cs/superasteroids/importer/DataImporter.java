package edu.byu.cs.superasteroids.importer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;

import edu.byu.cs.superasteroids.database.AsteroidTypeDAO;
import edu.byu.cs.superasteroids.database.BackgroundImageDAO;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.ViewableObject;

/**
 * Created by Jk on 2/12/2016.
 */
public class DataImporter implements  IGameDataImporter {

    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */
    @Override
    public boolean importData(InputStreamReader dataInputReader) {
        //TODO: Make it so that data can't be imported twice (AKA the tables are dropped and recreated)
        try {
            JSONObject rootObj = new JSONObject(makeString(dataInputReader));

            //The entire game object
            JSONObject gameObject = rootObj.getJSONObject("asteroidsGame");

            //The array of Asteroid Types
            JSONArray asteroidTypeArray = gameObject.getJSONArray("asteroids");
            //Iterate over the Asteroid Types and insert each into the database
            for(int i = 0; i < asteroidTypeArray.length(); i++){
                JSONObject currentAsteroid = asteroidTypeArray.getJSONObject(i);
                String name = currentAsteroid.getString("name");
                String imagePath = currentAsteroid.getString("image");
                int imageWidth = currentAsteroid.getInt("imageWidth");
                int imageHeight = currentAsteroid.getInt("imageHeight");
                String type = currentAsteroid.getString("type");

//                Log.i("JsonDomParserExample", "name: " + name +
//                        "\nimage: " + imagePath +
//                        "\nwidth: " + imageWidth +
//                        "\nheight: " + imageHeight +
//                        "\ntype: " + type);

                ViewableObject asteroidViewable = new ViewableObject(imagePath, imageWidth,imageHeight);
                AsteroidType newAsteroid = new AsteroidType(name, type, asteroidViewable, i + 1);
                AsteroidTypeDAO.getInstance().addItem(newAsteroid);
            }
            //The array of background images
            JSONArray backgroundObjectArray = gameObject.getJSONArray("objects");
            //Iterate over the background images and insert each into the database
            for (int i = 0; i < backgroundObjectArray.length(); i++) {
                String  backgroundImagePath = backgroundObjectArray.getString(i);
                BackgroundImageDAO.getInstance().addItem(backgroundImagePath);
//                Log.i("JsonDomParserExample", "Image path:");
//                Log.i("JsonDomParserExample", "           " + backgroundImagePath);
            }
        } catch (Exception e) {
            Log.i("JsonDomParserExample", e.getMessage());
            return false;
        }
        return true;
    }

        private static String makeString(InputStreamReader reader) throws IOException {

            StringBuilder sb = new StringBuilder();
            char[] buf = new char[512];

            int n = 0;
            while ((n = reader.read(buf)) > 0) {
                sb.append(buf, 0, n);
            }

            return sb.toString();
        }
}
