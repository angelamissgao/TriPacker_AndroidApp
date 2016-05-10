package com.example.tripacker.tripacker.DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tripacker.tripacker.entity.SpotEntity;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME1 = "User";
    public static final String TABLE_NAME2 = "Spot";
    public static final String TABLE_NAME3 = "Trip";
    public static final int DATABASE_VERSION = 1;
    public static final String T1COL_1 = "ID";
    static final String DATABASE_NAME = "TripackerDB";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    public void insertSpotRecord(SpotEntity spot_item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(T1COL_1, spot_item.getCity_id());
    }
}
