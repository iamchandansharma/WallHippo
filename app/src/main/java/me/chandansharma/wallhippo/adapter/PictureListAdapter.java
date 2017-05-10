package me.chandansharma.wallhippo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import me.chandansharma.wallhippo.R;
import me.chandansharma.wallhippo.model.PictureDetail;
import me.chandansharma.wallhippo.ui.PictureDetailScreen;

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

    private class PictureListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //get the reference of the all views
        private ImageView mPicture;
        private TextView mPictureAuthorName;
        private TextView mPictureLikes;
        private LinearLayout mPictureDetailLayout;
        private int mPictureIndex;

        private PictureListItemHolder(View itemView) {
            super(itemView);
            mPicture = (ImageView) itemView.findViewById(R.id.photo_id);
            mPictureAuthorName = (TextView) itemView.findViewById(R.id.photo_author_name);
            mPictureDetailLayout = (LinearLayout) itemView.findViewById(R.id.picture_detail_layout);
            mPictureLikes = (TextView) itemView.findViewById(R.id.photo_likes);

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
        }

        @Override
        public void onClick(View v) {
            //Intent for opening large image
            Intent pictureDetailIntent = new Intent(mContext, PictureDetailScreen.class);
            pictureDetailIntent.putExtra(Intent.EXTRA_TEXT,
                    mPictureDetailArrayList.get(mPictureIndex).getPhotoAuthorName() + "," +
                            mPictureDetailArrayList.get(mPictureIndex).getPhotoAuthorUserName() + "," +
                            mPictureDetailArrayList.get(mPictureIndex).getPhotoThumbnailUrl() + "," +
                            mPictureDetailArrayList.get(mPictureIndex).getUserProfilePictureUrl());
            mContext.startActivity(pictureDetailIntent);
        }
    }
}
