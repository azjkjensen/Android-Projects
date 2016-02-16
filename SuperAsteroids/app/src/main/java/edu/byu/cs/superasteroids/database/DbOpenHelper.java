package edu.byu.cs.superasteroids.database;
import android.content.Context;
import android.database.sqlite.*;

/**
 * Created by Jk on 2/12/2016.
 * This class extends the SQLiteOpenHelper class, which is used to create the tables and handle
 * database management.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    /**The name of the database */
    private static final String DB_NAME = "asteroids.sqlite";
    /**The version of the database */
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * This method drops (if they exist) and creates the tables necessary for the asteroids game.
     * @param db
     */
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_ASTEROID_TYPE =
                "create table book " +
                        "(" +
                        "   id integer not null primary key autoincrement," +
                        "   title varchar(255) not null," +
                        "   author varchar(255) not null," +
                        "   genre varchar(32) not null," +
                        "   constraint ck_genre check (genre in ('Unspecified', 'Fiction', 'NonFiction', 'HistoricalFiction'))" +
                        ")";

        db.execSQL(CREATE_ASTEROID_TYPE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
