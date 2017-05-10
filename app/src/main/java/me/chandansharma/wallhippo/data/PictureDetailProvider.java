package me.chandansharma.wallhippo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import me.chandansharma.wallhippo.data.PictureDetailContract.PictureDetailEntry;

/**
 * Created by iamcs on 2017-05-10.
 */

public class PictureDetailProvider extends ContentProvider {

    //TAG for the debug purpose
    public static final String TAG = PictureDetailProvider.class.getSimpleName();
    //Uri matcher for matching Uri
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    //Path for retrieve data from database
    private static final int PICTURE = 100;
    private static final int PICTURE_ID = 101;

    static {
        //Create Content Uri
        sUriMatcher.addURI(PictureDetailContract.CONTENT_AUTHORITY, PictureDetailContract.PATH_PICTURE, PICTURE);
        sUriMatcher.addURI(PictureDetailContract.CONTENT_AUTHORITY, PictureDetailContract.PATH_PICTURE + "/#", PICTURE_ID);
    }

    //Database object
    private PictureDetailDbHelper mPictureDetailDbHelper;

    @Override
    public boolean onCreate() {
        mPictureDetailDbHelper = new PictureDetailDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase placeDetailDatabase = mPictureDetailDbHelper.getReadableDatabase();

        //To get the Cursor
        Cursor cursor;
        int matchPlaceUri = sUriMatcher.match(uri);
        switch (matchPlaceUri) {
            case PICTURE:
                cursor = placeDetailDatabase.query(PictureDetailEntry.PICTURE_TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PICTURE_ID:
                selection = PictureDetailEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = placeDetailDatabase.query(PictureDetailEntry.PICTURE_TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //Match Uri inorder to insert data into database
        int matchPlaceUri = sUriMatcher.match(uri);
        switch (matchPlaceUri) {
            case PICTURE:
                return insertPictureDetails(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not support for " + uri);
        }
    }

    private Uri insertPictureDetails(Uri uri, ContentValues values) {
        //Get writable database for inserting value in database
        SQLiteDatabase placeDetailDatabase = mPictureDetailDbHelper.getWritableDatabase();

        //Insert new Place detail with given values
        long id = placeDetailDatabase.insert(PictureDetailEntry.PICTURE_TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(TAG, "Failed to insert data into DB" + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);

        //return the new uri with the ID append at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //Get writable database for deleting content from DB
        SQLiteDatabase placeDetailDatabase = mPictureDetailDbHelper.getWritableDatabase();

        //Track the number of row deleted from place_detail database
        int rowDeleted;

        //Match Uri in order to delete the data
        int matchPlaceUri = sUriMatcher.match(uri);
        switch (matchPlaceUri) {
            case PICTURE:
                //Delete all the Place details (All Row) from Database
                rowDeleted = placeDetailDatabase.delete(PictureDetailEntry.PICTURE_TABLE_NAME,
                        selection, selectionArgs);
                break;

            case PICTURE_ID:
                //Delete single Place detail from the Database
                selection = PictureDetailEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowDeleted = placeDetailDatabase.delete(PictureDetailEntry.PICTURE_TABLE_NAME,
                        selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        //If one or more rows deleted, then notify all listener that the data at
        //Given Url Change
        getContext().getContentResolver().notifyChange(uri, null);

        //Return number deleted row
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int matchPlaceUri = sUriMatcher.match(uri);
        switch (matchPlaceUri) {
            case PICTURE:
                return PictureDetailEntry.CONTENT_LIST_TYPE;
            case PICTURE_ID:
                return PictureDetailEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri + " With match " + matchPlaceUri);
        }
    }
}
