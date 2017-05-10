package me.chandansharma.wallhippo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import me.chandansharma.wallhippo.R;
import me.chandansharma.wallhippo.fragment.CollectionPictureListFragment;
import me.chandansharma.wallhippo.fragment.FavouritePictureFragment;
import me.chandansharma.wallhippo.fragment.PictureListFragment;

public class WallHippoMainScreen extends AppCompatActivity {

    //get the tag of the class
    public static final String TAG = PictureListFragment.class.getSimpleName();

    //All reference of the views
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_hippo_main_screen);

        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        setTitle(R.string.app_name);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ViewPager picturePages = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        if (picturePages != null)
            setViewPager(picturePages);
        mTabLayout.setupWithViewPager(picturePages);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            switch (i) {
                case 0:
                    mTabLayout.getTabAt(i).setIcon(R.drawable.ic_photo_white);
                    break;
                case 1:
                    mTabLayout.getTabAt(i).setIcon(R.drawable.ic_collections_white);
                    break;
                case 2:
                    mTabLayout.getTabAt(i).setIcon(R.drawable.ic_favorite_white);
                    break;
            }
        }
    }

    private void setViewPager(ViewPager picturePages) {
        ViewPagerAdapter picturePagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        picturePagerAdapter.addFragment(new PictureListFragment());
        picturePagerAdapter.addFragment(new CollectionPictureListFragment());
        picturePagerAdapter.addFragment(new FavouritePictureFragment());
        picturePages.setAdapter(picturePagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        //list of the fragment and fragment page title
        ArrayList<Fragment> pictureFragment = new ArrayList<>();
        ArrayList<String> pictureFragmentTitle = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pictureFragment.get(position);
        }

        @Override
        public int getCount() {
            return pictureFragment.size();
        }

        private void addFragment(Fragment fragment) {
            pictureFragment.add(fragment);
        }
    }
}
