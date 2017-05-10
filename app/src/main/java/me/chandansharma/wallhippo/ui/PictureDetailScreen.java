package me.chandansharma.wallhippo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import me.chandansharma.wallhippo.R;

public class PictureDetailScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail_screen);
        String intentData = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        String pictureDetail[] = intentData.split(",");
        ((TextView) findViewById(R.id.picture_author_name)).setText(pictureDetail[0]);
        ((TextView) findViewById(R.id.picture_author_user_name)).setText(pictureDetail[1]);
        Glide.with(this).load(pictureDetail[2]).into((ImageView) findViewById(R.id.full_picture));
        Glide.with(this).load(pictureDetail[3]).into((CircleImageView) findViewById(R.id.author_picture));
    }
}
