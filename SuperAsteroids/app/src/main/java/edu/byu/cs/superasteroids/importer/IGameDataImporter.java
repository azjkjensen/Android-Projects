package edu.byu.cs.superasteroids.importer;

import java.io.InputStreamReader;

/**
 * Created by Tyler on 3/13/2015.
 */
public interface IGameDataImporter {

    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */
    boolean importData(InputStreamReader dataInputReader);
}
