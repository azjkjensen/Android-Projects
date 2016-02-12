package edu.byu.cs.superasteroids.database;
import android.content.Context;
import android.database.sqlite.*;

/**
 * Created by Jk on 2/12/2016.
 */


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "asteroids.sqlite";
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        final String SQL =
                "create table book " +
                        "(" +
                        "   id integer not null primary key autoincrement," +
                        "   title varchar(255) not null," +
                        "   author varchar(255) not null," +
                        "   genre varchar(32) not null," +
                        "   constraint ck_genre check (genre in ('Unspecified', 'Fiction', 'NonFiction', 'HistoricalFiction'))" +
                        ")";

        db.execSQL(SQL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
