package me.chandansharma.wallhippo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.chandansharma.wallhippo.R;
import me.chandansharma.wallhippo.adapter.PictureListAdapter;
import me.chandansharma.wallhippo.model.PictureDetail;
import me.chandansharma.wallhippo.utils.ApiUrls;
import me.chandansharma.wallhippo.utils.AppController;

public class CollectionPictureDetailScreen extends AppCompatActivity {

    public static final String TAG = CollectionPictureDetailScreen.class.getSimpleName();

    //ArrayList of the all picture details
    ArrayList<PictureDetail> mPictureDetailsList = new ArrayList<>();

    //Picture Url
    private String mPictureUrl;
    private PictureListAdapter mPictureListAdapter;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_picture_detail_screen);

        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        setTitle(R.string.app_name);

        String collectionId = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        mPictureUrl = ApiUrls.PICTURE_BASE_URL + ApiUrls.COLLECTION_TAG + collectionId + "/" + ApiUrls.PHOTO_TAG
                + "?" + ApiUrls.API_KEY_TAG + "=" + ApiUrls.API_KEY + "&" + ApiUrls.PER_PAGE_ITEM_OFFSET + "=" + "30";
        Log.d(TAG, mPictureUrl);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        getPictureListData(mPictureUrl);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mPictureListAdapter = new PictureListAdapter(this, mPictureDetailsList);
        mGridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mPictureListAdapter);
    }

    private void getPictureListData(String pictureUrl) {
        mProgressBar.setVisibility(View.VISIBLE);
        //Tag to cancel the request
        String jsonArrayTag = "jsonArrayTag";
        JsonArrayRequest pictureJsonArray = new JsonArrayRequest(pictureUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mProgressBar.setVisibility(View.GONE);
                        try {
                            Log.d(TAG, response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject singlePictureDetailObject = (JSONObject) response.get(i);

                                PictureDetail singlePictureDetail = new PictureDetail(
                                        singlePictureDetailObject.getString("id"),
                                        singlePictureDetailObject.getInt("likes"),
                                        singlePictureDetailObject.getJSONObject("links").getString("download"),
                                        singlePictureDetailObject.getJSONObject("urls").getString("regular"),
                                        singlePictureDetailObject.getJSONObject("user").getString("name"),
                                        singlePictureDetailObject.getJSONObject("user").getString("username"),
                                        singlePictureDetailObject.getString("color"),
                                        singlePictureDetailObject.getJSONObject("user").
                                                getJSONObject("profile_image").getString("medium"));
                                mPictureDetailsList.add(singlePictureDetail);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mPictureListAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, error.getMessage());
                    }
                });
        //Adding request to the queue
        AppController.getInstance().addToRequestQueue(pictureJsonArray, jsonArrayTag);
    }
}
