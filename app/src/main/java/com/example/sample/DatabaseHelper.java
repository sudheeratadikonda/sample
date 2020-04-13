package com.example.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 11/01/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "india.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_State = "state";
    private static final String TABLE_district = "district";
    private static final String TABLE_mandal = "mandal";
   /* private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "name";
    private static final String KEY_HOBBY = "hobby";
    private static final String KEY_CITY = "city";
*/
    /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/

    String tb1="CREATE TABLE "+TABLE_State
            +"( state_code TEXT PRIMARY KEY, state_name TEXT);";
    String tb2="CREATE TABLE "+TABLE_district
            +"( dist_code TEXT PRIMARY KEY, dist_name TEXT, state_code TEXT, state_name TEXT, CONSTRAINT fk_column  FOREIGN  KEY(state_code) references state(state_code));";
    String tb3="CREATE TABLE "+TABLE_mandal+
            "(mandal_code Text PRIMARY KEY, mandal_name TEXT, dist_code TEXT, dist_name TEXT, state_code TEXT, state_name TEXT, CONSTRAINT fk_column1 FOREIGN  KEY(state_code) references state(state_code), CONSTRAINT fk_column2 FOREIGN KEY(dist_code) references district(dist_code));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", tb1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tb1);
        db.execSQL(tb2);
        db.execSQL(tb3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_State + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_district + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_mandal + "'");
        onCreate(db);
    }
    public boolean stateins(String st_name,String st_cd)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("state_code",st_cd);
        contentValues.put("state_name",st_name);
        long result = db.insert(TABLE_State, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            Log.d("insertion", "data success");
            return true;
        }
    }
    public boolean distins(String dist_cd,String dist_name,String st_cd,String st_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dist_code",dist_cd);
        contentValues.put("dist_name",dist_name);
        contentValues.put("state_code",st_cd);
        contentValues.put("state_name",st_name);
        long result = db.insert(TABLE_district, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            Log.d("insertion", "data success");
            return true;
        }
    }
    public boolean mndlins(String mndl_code,String mndl_name,String dist_cd,String dist_name,String st_cd,String st_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mandal_code",mndl_code);
        contentValues.put("mandal_name",mndl_name);
        contentValues.put("dist_code",dist_cd);
        contentValues.put("dist_name",dist_name);
        contentValues.put("state_code",st_cd);
        contentValues.put("state_name",st_name);
        long result = db.insert(TABLE_mandal, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            Log.d("insertion", "data success");
            return true;
        }
    }
   /* public void addUser(String name, String hobby, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        //adding user name in users table
        ContentValues values = new ContentValues();
        values.put("state_name","sudheera" );
        values.put("state_code","su");
        // db.insert(TABLE_USER, null, values);
        long id = db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        //adding user hobby in users_hobby table
        ContentValues valuesHobby = new ContentValues();
        valuesHobby.put("state_name","sudheera" );
        valuesHobby.put("state_code","su");
        valuesHobby.put("district_name","gnt");
        valuesHobby.put("district_code","07");
        db.insert(TABLE_USER_HOBBY, null, valuesHobby);

        //adding user city in users_city table
        ContentValues valuesCity = new ContentValues();
        valuesCity.put("state_name","sudheera" );
        valuesCity.put("state_code","su");
        valuesCity.put("district_name","gnt");
        valuesCity.put("district_code","07");
        valuesCity.put("mandal_name","chp");
        valuesCity.put("mandal_code","ch");
        db.insert(TABLE_USER_CITY, null, valuesCity);
    }*/

    /*public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                userModel.setName(c.getString(c.getColumnIndex(KEY_FIRSTNAME)));

                //getting user hobby where id = id from user_hobby table
                String selectHobbyQuery = "SELECT  * FROM " + TABLE_USER_HOBBY +" WHERE "+KEY_ID+" = "+ userModel.getId();
                Log.d("oppp",selectHobbyQuery);
                //SQLiteDatabase dbhobby = this.getReadableDatabase();
                Cursor cHobby = db.rawQuery(selectHobbyQuery, null);

                if (cHobby.moveToFirst()) {
                    do {
                        userModel.setHobby(cHobby.getString(cHobby.getColumnIndex(KEY_HOBBY)));
                    } while (cHobby.moveToNext());
                }

                //getting user city where id = id from user_city table
                String selectCityQuery = "SELECT  * FROM " + TABLE_USER_CITY+" WHERE "+KEY_ID+" = "+ userModel.getId();;
                //SQLiteDatabase dbCity = this.getReadableDatabase();
                Cursor cCity = db.rawQuery(selectCityQuery, null);

                if (cCity.moveToFirst()) {
                    do {
                        userModel.setCity(cCity.getString(cCity.getColumnIndex(KEY_CITY)));
                    } while (cCity.moveToNext());
                }

                // adding to Students list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }*/    }