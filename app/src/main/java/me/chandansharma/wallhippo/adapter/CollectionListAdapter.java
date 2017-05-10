package me.chandansharma.wallhippo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import de.hdodenhof.circleimageview.CircleImageView;
import me.chandansharma.wallhippo.R;
import me.chandansharma.wallhippo.model.CollectionDetail;
import me.chandansharma.wallhippo.ui.CollectionPictureDetailScreen;

/**
 * Created by iamcs on 2017-04-27.
 */

public class CollectionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Context and reference of the arrayList
     */
    private Context mContext;
    private ArrayList<CollectionDetail> mCollectionDetailArrayList;
    private int mCollectionIndex;

    // Public Constructor
    public CollectionListAdapter(Context context, ArrayList<CollectionDetail> collectionDetails) {
        mContext = context;
        mCollectionDetailArrayList = collectionDetails;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectionListItemHolder(LayoutInflater
                .from(mContext).inflate(R.layout.collection_screen_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CollectionListItemHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return mCollectionDetailArrayList.size();
    }

    private class CollectionListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //get the reference of the all views
        private ImageView mPicture;
        private TextView mCollectionTitle;
        private CircleImageView mUserProfilePicture;
        private LinearLayout mPictureDetailLayout;
        private TextView mCollectionAuthorName;

        private CollectionListItemHolder(View itemView) {
            super(itemView);

            mPicture = (ImageView) itemView.findViewById(R.id.photo_id);
            mCollectionTitle = (TextView) itemView.findViewById(R.id.photo_album_name);
            mUserProfilePicture = (CircleImageView) itemView.findViewById(R.id.user_profile_picture);
            mPictureDetailLayout = (LinearLayout) itemView.findViewById(R.id.picture_detail_layout);
            mCollectionAuthorName = (TextView) itemView.findViewById(R.id.photo_author_name);

            mPicture.setOnClickListener(this);
        }

        //Bind the view
        private void bindView(int position) {
            //index of the picture position
            mCollectionIndex = position;

            mPictureDetailLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

            Glide.with(mContext).load(mCollectionDetailArrayList.get(mCollectionIndex).getCollectionThumbnailUrl())
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
            Glide.with(mContext)
                    .load(mCollectionDetailArrayList.get(mCollectionIndex).getCollectionAuthorProfilePictureUrl())
                    .into(mUserProfilePicture);
            mCollectionTitle.setText(mCollectionDetailArrayList.get(mCollectionIndex).getCollectionTitle());
            mCollectionAuthorName.setText("by " + mCollectionDetailArrayList.get(mCollectionIndex).getCollectionAuthorName());
        }

        @Override
        public void onClick(View v) {
            //Intent for opening large image
            Intent pictureDetailIntent = new Intent(mContext, CollectionPictureDetailScreen.class);
            pictureDetailIntent.putExtra(Intent.EXTRA_TEXT,
                    mCollectionDetailArrayList.get(mCollectionIndex).getCollectionId());
            Log.d("CS", mCollectionDetailArrayList.get(mCollectionIndex).getCollectionId());
            mContext.startActivity(pictureDetailIntent);
        }
    }
}
