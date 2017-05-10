package me.chandansharma.wallhippo.fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import me.chandansharma.wallhippo.R;
import me.chandansharma.wallhippo.adapter.PictureListAdapter;
import me.chandansharma.wallhippo.data.PictureDetailContract.PictureDetailEntry;
import me.chandansharma.wallhippo.model.PictureDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritePictureFragment extends Fragment {

    //get the tag of the class
    public static final String TAG = PictureListFragment.class.getSimpleName();

    //ArrayList of the all picture details
    ArrayList<PictureDetail> mPictureDetailArrayList = new ArrayList<>();

    //Picture Url
    private String mPictureUrl;
    private PictureListAdapter mPictureListAdapter;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_picture_list, container, false);

        getFavouritePictureData();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mPictureListAdapter = new PictureListAdapter(getActivity(), mPictureDetailArrayList);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mPictureListAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPictureDetailArrayList.clear();
        getFavouritePictureData();
        mPictureListAdapter.notifyDataSetChanged();
    }

    private void getFavouritePictureData() {
        //Uri for requesting data from database
        Uri pictureDetailUri = PictureDetailEntry.CONTENT_URI;

        //Cursor Object to get the result from database
        Cursor pictureDataCursor = getActivity().getContentResolver().query(pictureDetailUri,
                null, null, null, null);

        if (pictureDataCursor != null) {
            if (pictureDataCursor.moveToFirst()) {
                do {
                    PictureDetail singlePictureDetail = new PictureDetail(
                            pictureDataCursor.getString(pictureDataCursor.getColumnIndex(
                                    PictureDetailEntry.COLUMN_PICTURE_ID)),
                            pictureDataCursor.getInt(pictureDataCursor.getColumnIndex(
                                    PictureDetailEntry.COLUMN_PICTURE_LIKES)),
                            pictureDataCursor.getString(pictureDataCursor.getColumnIndex(
                                    PictureDetailEntry.COLUMN_PICTURE_DOWNLOAD_URL)),
                            pictureDataCursor.getString(pictureDataCursor.getColumnIndex(
                                    PictureDetailEntry.COLUMN_PICTURE_THUMBNAIL_URL)),
                            pictureDataCursor.getString(pictureDataCursor.getColumnIndex(
                                    PictureDetailEntry.COLUMN_PICTURE_AUTHOR_NAME)),
                            pictureDataCursor.getString(pictureDataCursor.getColumnIndex(
                                    PictureDetailEntry.COLUMN_PICTURE_AUTHOR_USERNAME)),
                            pictureDataCursor.getString(pictureDataCursor.getColumnIndex(
                                    PictureDetailEntry.COLUMN_PICTURE_COLOR)),
                            pictureDataCursor.getString(pictureDataCursor.getColumnIndex(
                                    PictureDetailEntry.COLUMN_USER_PROFILE_PICTURE_URL)));
                    mPictureDetailArrayList.add(singlePictureDetail);
                } while (pictureDataCursor.moveToNext());
            }
        }
        pictureDataCursor.close();
    }
}
