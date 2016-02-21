package edu.byu.cs.superasteroids.importer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;

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
//        AsteroidTypeDAO asteroidTypeDAO = new AsteroidTypeDAO(MainActivity.db);
//        asteroidTypeDAO.


//        public static void run(Reader reader) throws Exception {
        try {
            JSONObject rootObj = new JSONObject(makeString(dataInputReader));
//            rootObj.getJSONObject("asteroidsGame").getJSONArray("Objects");
            JSONObject gameObject = rootObj.getJSONObject("asteroidsGame");
            JSONArray backgroundObjectArray = gameObject.getJSONArray("objects");
            for (int i = 0; i < backgroundObjectArray.length(); ++i) {
                String  backgroundImagePath = backgroundObjectArray.getString(i);
//                JSONObject cdObj = elemObj.getJSONObject("CD");
//
//                String title = cdObj.getString("TITLE");
//                String artist = cdObj.getString("ARTIST");

                Log.i("JsonDomParserExample", "Image path:\n");
                Log.i("JsonDomParserExample", "     " + backgroundImagePath);
            }
        } catch (Exception e) {
            Log.i("JsonDomParserExample", e.getMessage());
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
