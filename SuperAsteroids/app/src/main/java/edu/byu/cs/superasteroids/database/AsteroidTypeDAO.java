package edu.byu.cs.superasteroids.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.ViewableObject;

/**
 * Created by Jk on 2/12/2016.\n
 * This is our database access object class. It is used to let the model interface with the
 * database.
 */
public class AsteroidTypeDAO {

    private SQLiteDatabase db;

    public AsteroidTypeDAO(SQLiteDatabase db) {
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
     * Takes <code>asteroid</code> and inserts it into the proper table
     * @param asteroid
     */
    public void addItem(AsteroidType asteroid){
        ContentValues values = new ContentValues();
        values.put("name", asteroid.getName());
        values.put("type", asteroid.getType());
        values.put("image", asteroid.getViewableInfo().getImage());
        values.put("image_height", asteroid.getViewableInfo().getImageHeight());
        values.put("image_width", asteroid.getViewableInfo().getImageWidth());
        db.insert("asteroidTypes", null, values);
    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public Set<AsteroidType> getByID(int id){
        final String SQLGet = "SELECT NAME, TYPE, IMAGE, IMAGE_HEIGHT," +
                "IMAGE_WIDTH FROM asteroidTypes WHERE ID = " + Integer.toString(id);

        Set<AsteroidType> result = new HashSet<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{Integer.toString(id)});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AsteroidType asteroidType = new AsteroidType();

                asteroidType.setID(cursor.getInt(0));
                asteroidType.setName(cursor.getString(1));
                asteroidType.setType(cursor.getString(2));
                asteroidType.setViewableInfo(
                        new ViewableObject(
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5)
                ));

                result.add(asteroidType);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }

    /**
     * Gets all AsteroidType items from the table.
     * @return the set of all AsteroidTypes from the table
     */
    public Set<AsteroidType> getAll(){
        final String SQLGet = "SELECT * FROM asteroidTypes";

        Set<AsteroidType> result = new HashSet<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AsteroidType asteroidType = new AsteroidType();

                asteroidType.setID(cursor.getInt(0));
                asteroidType.setName(cursor.getString(1));
                asteroidType.setType(cursor.getString(2));
                asteroidType.setViewableInfo(
                        new ViewableObject(
                                cursor.getString(3),
                                cursor.getInt(4),
                                cursor.getInt(5)
                        ));

                result.add(asteroidType);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }
    }



//    public boolean addAsteroidType(Book book) {
//        ContentValues values = new ContentValues();
//        values.put("title", book.getTitle());
//        values.put("author", book.getAuthor());
//        values.put("genre", book.getGenre().toString());
//
//        long id = db.insert("book", null, values);
//        if (id >= 0) {
//            book.setID(id);
//            return true;
//        }
//        else {
//            return false;
//        }
//    }


//    public Set<Book> getBooksByAuthor(String author) {
//        final String SQL = "select id, title, author, genre " +
//                "from book " +
//                "where author = ?";
//
//        Set<Book> result = new HashSet<>();
//
//        Cursor cursor = db.rawQuery(SQL, new String[]{author});
//        try {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                Book book = new Book();
//
//                book.setID(cursor.getLong(0));
//                book.setTitle(cursor.getString(1));
//                book.setAuthor(cursor.getString(2));
//                book.setGenre(Genre.valueOf(cursor.getString(3)));
//
//                result.add(book);
//
//                cursor.moveToNext();
//            }
//        }
//        finally {
//            cursor.close();
//        }
//
//        return result;
//    }
//}
