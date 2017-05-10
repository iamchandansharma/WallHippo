package me.chandansharma.wallhippo.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.chandansharma.wallhippo.R;
import me.chandansharma.wallhippo.data.PictureDetailContract.PictureDetailEntry;
import me.chandansharma.wallhippo.model.PictureDetail;
import me.chandansharma.wallhippo.utils.ApiUrls;

public class PictureDetailScreen extends AppCompatActivity {

    private ImageView mFavouriteImageIcon;
    private PictureDetail mCurrentPictureDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail_screen);
        mCurrentPictureDetail = getIntent().getParcelableExtra(ApiUrls.PICTURE_DATA_KEY);

        ((TextView) findViewById(R.id.picture_author_name)).setText(mCurrentPictureDetail.getPhotoAuthorName());
        ((TextView) findViewById(R.id.picture_author_user_name)).setText(mCurrentPictureDetail.getPhotoAuthorUserName());
        Glide.with(this).load(mCurrentPictureDetail.getPhotoThumbnailUrl()).into((ImageView) findViewById(R.id.full_picture));
        Glide.with(this).load(mCurrentPictureDetail.getUserProfilePictureUrl()).into((CircleImageView) findViewById(R.id.author_picture));

        findViewById(R.id.picture_download_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureDownloadIntent = new Intent(Intent.ACTION_VIEW);
                pictureDownloadIntent.setData(Uri.parse(mCurrentPictureDetail.getPhotoDownloadUrl()));
                startActivity(pictureDownloadIntent);
            }
        });
        mFavouriteImageIcon = (ImageView) findViewById(R.id.picture_favourite_button);

        if (pictureInDatabase(mCurrentPictureDetail.getPhotoId()))
            mFavouriteImageIcon.setImageDrawable(ContextCompat.getDrawable(
                    this, R.drawable.ic_favorite_white));
        else
            mFavouriteImageIcon.setImageDrawable(ContextCompat.getDrawable(
                    this, R.drawable.ic_favorite_border_white));

        mFavouriteImageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFavouriteImageIcon.getDrawable().getConstantState().equals(
                        ContextCompat.getDrawable(PictureDetailScreen.this, R.drawable.ic_favorite_border_white_48dp)
                                .getConstantState())) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(PictureDetailEntry.COLUMN_PICTURE_ID,
                            mCurrentPictureDetail.getPhotoId());
                    contentValues.put(PictureDetailEntry.COLUMN_PICTURE_LIKES,
                            mCurrentPictureDetail.getPhotoLikes());
                    contentValues.put(PictureDetailEntry.COLUMN_PICTURE_DOWNLOAD_URL,
                            mCurrentPictureDetail.getPhotoDownloadUrl());
                    contentValues.put(PictureDetailEntry.COLUMN_PICTURE_THUMBNAIL_URL,
                            mCurrentPictureDetail.getPhotoThumbnailUrl());
                    contentValues.put(PictureDetailEntry.COLUMN_PICTURE_AUTHOR_NAME,
                            mCurrentPictureDetail.getPhotoAuthorName());
                    contentValues.put(PictureDetailEntry.COLUMN_PICTURE_AUTHOR_USERNAME,
                            mCurrentPictureDetail.getPhotoAuthorUserName());
                    contentValues.put(PictureDetailEntry.COLUMN_PICTURE_COLOR,
                            mCurrentPictureDetail.getPhotoColor());
                    contentValues.put(PictureDetailEntry.COLUMN_USER_PROFILE_PICTURE_URL,
                            mCurrentPictureDetail.getUserProfilePictureUrl());

                    //insert data into database
                    getContentResolver().insert(PictureDetailEntry.CONTENT_URI,
                            contentValues);
                    Toast.makeText(PictureDetailScreen.this, "Picture Added to Favourite List",
                            Toast.LENGTH_SHORT).show();
                    mFavouriteImageIcon.setImageDrawable(ContextCompat
                            .getDrawable(PictureDetailScreen.this, R.drawable.ic_favorite_white));
                } else {
                    Uri pictureDetailUri = PictureDetailEntry.CONTENT_URI;
                    String selection = PictureDetailEntry.COLUMN_PICTURE_ID + "=?";
                    String[] selectionArgs = new String[]{String.valueOf(
                            mCurrentPictureDetail.getPhotoId())};

                    //Delete query
                    getContentResolver().delete(pictureDetailUri, selection,
                            selectionArgs);

                    mFavouriteImageIcon.setImageDrawable(ContextCompat
                            .getDrawable(PictureDetailScreen.this, R.drawable.ic_favorite_border_white));
                    Toast.makeText(PictureDetailScreen.this, "Picture Removed from Favourite List",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean pictureInDatabase(String photoId) {
        //Uri fore requesting data
        Uri pictureContentUri = PictureDetailEntry.CONTENT_URI;

        //ArrayList for all store placeId information
        ArrayList<String> pictureDetailId = new ArrayList<>();

        //Cursor Object to get result from database
        Cursor pictureDataCursor = getContentResolver().query(pictureContentUri,
                null, null, null, null);
        if (pictureDataCursor != null) {
            if (pictureDataCursor.moveToFirst()) {
                do {
                    String id = pictureDataCursor.getString(pictureDataCursor.getColumnIndex(
                            PictureDetailEntry.COLUMN_PICTURE_ID));
                    pictureDetailId.add(id);
                } while (pictureDataCursor.moveToNext());
            } else
                return false;
        } else
            return false;
        if (pictureDetailId.size() != 0) {
            for (int i = 0; i < pictureDetailId.size(); i++) {
                if (pictureDetailId.get(i).equals(photoId))
                    return true;
            }
        }
        pictureDataCursor.close();
        return false;
    }
}
