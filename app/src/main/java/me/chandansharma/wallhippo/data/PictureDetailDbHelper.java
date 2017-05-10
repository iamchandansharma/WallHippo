package me.chandansharma.wallhippo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.chandansharma.wallhippo.data.PictureDetailContract.PictureDetailEntry;

/**
 * Created by iamcs on 2017-05-10.
 */

public class PictureDetailDbHelper extends SQLiteOpenHelper {

    //TAG for the class
    public static final String TAG = PictureDetailDbHelper.class.getSimpleName();

    //Place Database Name and Version
    public static final String DATABASE_NAME = "picture_detail.db";
    public static final int DATABASE_VERSION = 1;

    public PictureDetailDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Query for Place Detail
        String SQL_CREATE_PICTURE_DETAIL_QUERY =
                "CREATE TABLE " + PictureDetailEntry.PICTURE_TABLE_NAME + "(" +
                        PictureDetailEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PictureDetailEntry.COLUMN_PICTURE_ID + " TEXT, " +
                        PictureDetailEntry.COLUMN_PICTURE_LIKES + " INTEGER, " +
                        PictureDetailEntry.COLUMN_PICTURE_DOWNLOAD_URL + " TEXT, " +
                        PictureDetailEntry.COLUMN_PICTURE_THUMBNAIL_URL + " TEXT, " +
                        PictureDetailEntry.COLUMN_PICTURE_AUTHOR_NAME + " TEXT, " +
                        PictureDetailEntry.COLUMN_PICTURE_AUTHOR_USERNAME + " TEXT, " +
                        PictureDetailEntry.COLUMN_PICTURE_COLOR + " TEXT, " +
                        PictureDetailEntry.COLUMN_USER_PROFILE_PICTURE_URL + " TEXT" + ")";
        db.execSQL(SQL_CREATE_PICTURE_DETAIL_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_PICTURE_DETAIL_QUERY =
                "DELETE FROM " + PictureDetailEntry.PICTURE_TABLE_NAME;
        db.execSQL(SQL_DELETE_PICTURE_DETAIL_QUERY);
    }
}
