package edu.byu.cs.superasteroids.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.model.AsteroidType;

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

    public void addItem(){

    }

    /**
     * Finds the item in the database by id.
     * @param id
     */
    public void getByID(int id){

    }


//    AsteroidType asteroid

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
}
