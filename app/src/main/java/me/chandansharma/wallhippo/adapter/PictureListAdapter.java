package me.chandansharma.wallhippo.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import me.chandansharma.wallhippo.R;
import me.chandansharma.wallhippo.data.PictureDetailContract.PictureDetailEntry;
import me.chandansharma.wallhippo.model.PictureDetail;
import me.chandansharma.wallhippo.ui.PictureDetailScreen;
import me.chandansharma.wallhippo.utils.ApiUrls;

/**
 * Created by iamcs on 2017-05-10.
 */

public class PictureListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Context and reference of the arrayList
     */
    private Context mContext;
    private ArrayList<PictureDetail> mPictureDetailArrayList;

    // Public Constructor
    public PictureListAdapter(Context context, ArrayList<PictureDetail> pictureDetails) {
        mContext = context;
        mPictureDetailArrayList = pictureDetails;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureListItemHolder(LayoutInflater
                .from(mContext).inflate(R.layout.picture_screen_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PictureListItemHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return mPictureDetailArrayList.size();
    }

    private boolean pictureInDatabase(String photoId) {
        //Uri fore requesting data
        Uri pictureContentUri = PictureDetailEntry.CONTENT_URI;

        //ArrayList for all store placeId information
        ArrayList<String> pictureDetailId = new ArrayList<>();

        //Cursor Object to get result from database
        Cursor pictureDataCursor = mContext.getContentResolver().query(pictureContentUri,
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

    private class PictureListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //get the reference of the all views
        private ImageView mPicture;
        private TextView mPictureAuthorName;
        private TextView mPictureLikes;
        private LinearLayout mPictureDetailLayout;
        private int mPictureIndex;
        private ImageView mFavouriteIconImageView;

        private PictureListItemHolder(View itemView) {
            super(itemView);
            mPicture = (ImageView) itemView.findViewById(R.id.photo_id);
            mPictureAuthorName = (TextView) itemView.findViewById(R.id.photo_author_name);
            mPictureDetailLayout = (LinearLayout) itemView.findViewById(R.id.picture_detail_layout);
            mPictureLikes = (TextView) itemView.findViewById(R.id.photo_likes);
            mFavouriteIconImageView = (ImageView) itemView.findViewById(R.id.favourite_button);

            mPicture.setOnClickListener(this);
        }

        //Bind the view
        private void bindView(int position) {
            //index of the picture position
            mPictureIndex = position;

            mPictureDetailLayout.setBackgroundColor(ContextCompat.getColor(mContext,
                    R.color.colorPrimary));
            Glide.with(mContext).load(mPictureDetailArrayList.get(mPictureIndex).getPhotoThumbnailUrl())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mPicture.setImageBitmap(resource);

                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                                    if (darkVibrantSwatch != null) {
                                        mPictureDetailLayout.setBackgroundColor(darkVibrantSwatch.getRgb());
                                    }
                                }
                            });
                        }
                    });
            mPictureAuthorName.setText(mPictureDetailArrayList.get(mPictureIndex).getPhotoAuthorName());
            mPictureLikes.setText(String.valueOf(mPictureDetailArrayList.get(mPictureIndex).getPhotoLikes()) + "\tLikes");

            if (pictureInDatabase(mPictureDetailArrayList.get(mPictureIndex).getPhotoId()))
                mFavouriteIconImageView.setImageDrawable(ContextCompat.getDrawable(
                        mContext, R.drawable.ic_favorite_white));
            else
                mFavouriteIconImageView.setImageDrawable(ContextCompat.getDrawable(
                        mContext, R.drawable.ic_favorite_border_white));

            mFavouriteIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFavouriteIconImageView.getDrawable().getConstantState().equals(
                            ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_border_white)
                                    .getConstantState())) {

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(PictureDetailEntry.COLUMN_PICTURE_ID,
                                mPictureDetailArrayList.get(mPictureIndex).getPhotoId());
                        contentValues.put(PictureDetailEntry.COLUMN_PICTURE_LIKES,
                                mPictureDetailArrayList.get(mPictureIndex).getPhotoLikes());
                        contentValues.put(PictureDetailEntry.COLUMN_PICTURE_DOWNLOAD_URL,
                                mPictureDetailArrayList.get(mPictureIndex).getPhotoDownloadUrl());
                        contentValues.put(PictureDetailEntry.COLUMN_PICTURE_THUMBNAIL_URL,
                                mPictureDetailArrayList.get(mPictureIndex).getPhotoThumbnailUrl());
                        contentValues.put(PictureDetailEntry.COLUMN_PICTURE_AUTHOR_NAME,
                                mPictureDetailArrayList.get(mPictureIndex).getPhotoAuthorName());
                        contentValues.put(PictureDetailEntry.COLUMN_PICTURE_AUTHOR_USERNAME,
                                mPictureDetailArrayList.get(mPictureIndex).getPhotoAuthorUserName());
                        contentValues.put(PictureDetailEntry.COLUMN_PICTURE_COLOR,
                                mPictureDetailArrayList.get(mPictureIndex).getPhotoColor());
                        contentValues.put(PictureDetailEntry.COLUMN_USER_PROFILE_PICTURE_URL,
                                mPictureDetailArrayList.get(mPictureIndex).getUserProfilePictureUrl());

                        //insert data into database
                        mContext.getContentResolver().insert(PictureDetailEntry.CONTENT_URI,
                                contentValues);
                        Toast.makeText(mContext, "Picture Added to Favourite List",
                                Toast.LENGTH_SHORT).show();
                        mFavouriteIconImageView.setImageDrawable(ContextCompat
                                .getDrawable(mContext, R.drawable.ic_favorite_white_36dp));
                    } else {
                        Uri pictureDetailUri = PictureDetailEntry.CONTENT_URI;
                        String selection = PictureDetailEntry.COLUMN_PICTURE_ID + "=?";
                        String[] selectionArgs = new String[]{String.valueOf(
                                mPictureDetailArrayList.get(mPictureIndex).getPhotoId())};

                        //Delete query
                        mContext.getContentResolver().delete(pictureDetailUri, selection,
                                selectionArgs);

                        mFavouriteIconImageView.setImageDrawable(ContextCompat
                                .getDrawable(mContext, R.drawable.ic_favorite_border_white));
                        Toast.makeText(mContext, "Picture Removed from Favourite List",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            //Intent for opening large image
            Intent pictureDetailIntent = new Intent(mContext, PictureDetailScreen.class);
            pictureDetailIntent.putExtra(ApiUrls.PICTURE_DATA_KEY,
                    mPictureDetailArrayList.get(mPictureIndex));
            mContext.startActivity(pictureDetailIntent);
        }
    }
}

