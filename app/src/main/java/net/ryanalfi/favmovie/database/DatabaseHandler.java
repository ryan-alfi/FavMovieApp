package net.ryanalfi.favmovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imac on 8/23/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myMovie";

    // Contacts table name
    private static final String TABLE_FAV = "favorite";

    // Contacts Table Columns names
    private static final String KEY_IDMOVIE = "idmovie";
    private static final String KEY_STATEMOVIE = "statemovie";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_FAV_TABLE = "CREATE TABLE " + TABLE_FAV + "("
                + KEY_IDMOVIE + " INTEGER PRIMARY KEY,"
                + KEY_STATEMOVIE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_FAV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);

        onCreate(sqLiteDatabase);
    }

    public void addFavoriteToDB(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDMOVIE, id);
        values.put(KEY_STATEMOVIE, "true");

        db.insert(TABLE_FAV,null,values);
        db.close();
    }

    public Myfavmovie getFavMovie(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FAV, new String[] {
                KEY_IDMOVIE, KEY_STATEMOVIE }, KEY_IDMOVIE + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor.moveToFirst()){
            Myfavmovie fav = new Myfavmovie(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
            return fav;
        }
        return null;

    }

    public List<Myfavmovie> getAllFavMovie(){
        List<Myfavmovie> favList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_FAV;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Myfavmovie fav = new Myfavmovie();
                fav.setIdmovie(Integer.parseInt(cursor.getString(0)));
                fav.setStatemovie(cursor.getString(1));

                favList.add(fav);
            }while (cursor.moveToNext());
        }

        return favList;
    }

    public int[] getAllFavMovieId(){
        int[] favId;

        String selectQuery = "SELECT idmovie FROM " +TABLE_FAV;
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        favId = new int[cursor.getCount()];
        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                favId[i] = Integer.parseInt(cursor.getString(0));
                i++;
            }while (cursor.moveToNext());
        }

        return favId;
    }

    public int updateFavMovie(Myfavmovie favMovie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDMOVIE, favMovie.getIdmovie());
        values.put(KEY_STATEMOVIE, favMovie.getStatemovie());

        return db.update(TABLE_FAV, values, KEY_IDMOVIE + " = ?",
                new String[] {String.valueOf(favMovie.getIdmovie())});
    }

    public void deleteFavMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAV, KEY_IDMOVIE + " = ?",
                new String[] { String.valueOf(id)});
        db.close();
    }

    public int getFavMovieCount(){
        String countQuery = "SELECT * FROM " + TABLE_FAV;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
