package me.chandansharma.wallhippo.data;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by iamcs on 2017-05-10.
 */

public class PictureDetailContract {

    //Content Authority
    public static final String CONTENT_AUTHORITY = "me.chandansharma.wallhippo";
    //Base Uri
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //content path
    public static final String PATH_PICTURE = "picture";

    private PictureDetailContract() {
    }

    public static final class PictureDetailEntry {

        //Content Uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PICTURE);

        //All the constant that are used to store the picture details in table
        public static final String PICTURE_TABLE_NAME = "picture_detail";

        public static final String _ID = "_id";
        public static final String COLUMN_PICTURE_ID = "picture_id";
        public static final String COLUMN_PICTURE_AUTHOR_NAME = "picture_author_name";
        public static final String COLUMN_PICTURE_LIKES = "picture_likes";
        public static final String COLUMN_PICTURE_DOWNLOAD_URL = "picture_download_url";

        //The MIME type of the Place
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PICTURE;
        //The MIME type of the Single Place
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PICTURE;
    }

}
