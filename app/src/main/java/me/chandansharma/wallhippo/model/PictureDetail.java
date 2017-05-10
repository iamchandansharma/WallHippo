package me.chandansharma.wallhippo.model;

/**
 * Created by iamcs on 2017-04-26.
 */

public class PictureDetail {

    /**
     * All the member variable that use to store information
     */
    private String mPhotoId;
    private int mPhotoLikes;
    private String mPhotoDownloadUrl;
    private String mPhotoThumbnailUrl;
    private String mPhotoAuthorName;
    private String mPhotoColor;
    private String mPhotoAuthorUserName;
    private String mUserProfilePictureUrl;

    public PictureDetail(String mPhotoId, int mPhotoLikes, String mPhotoDownloadUrl,
                         String mPhotoThumbnailUrl, String mPhotoAuthorName,
                         String mPictureAuthorUserName, String mPictureColor,
                         String mUserProfilePictureUrl){
        this.mPhotoId = mPhotoId;
        this.mPhotoLikes = mPhotoLikes;
        this.mPhotoDownloadUrl = mPhotoDownloadUrl;
        this.mPhotoThumbnailUrl = mPhotoThumbnailUrl;
        this.mPhotoAuthorName = mPhotoAuthorName;
        this.mPhotoAuthorUserName = mPictureAuthorUserName;
        this.mPhotoColor = mPictureColor;
        this.mUserProfilePictureUrl = mUserProfilePictureUrl;
    }


    public String getPhotoId() {
        return mPhotoId;
    }

    public void setPhotoId(String photoId) {
        mPhotoId = photoId;
    }

    public int getPhotoLikes() {
        return mPhotoLikes;
    }

    public void setPhotoLikes(int photoLikes) {
        mPhotoLikes = photoLikes;
    }

    public String getPhotoDownloadUrl() {
        return mPhotoDownloadUrl;
    }

    public void setPhotoDownloadUrl(String photoDownloadUrl) {
        mPhotoDownloadUrl = photoDownloadUrl;
    }

    public String getPhotoThumbnailUrl() {
        return mPhotoThumbnailUrl;
    }

    public void setPhotoThumbnailUrl(String photoThumbnailUrl) {
        mPhotoThumbnailUrl = photoThumbnailUrl;
    }

    public String getPhotoAuthorName() {
        return mPhotoAuthorName;
    }

    public void setPhotoAuthorName(String photoAuthorName) {
        mPhotoAuthorName = photoAuthorName;
    }

    public String getPhotoColor() {
        return mPhotoColor;
    }

    public void setPhotoColor(String photoColor) {
        mPhotoColor = photoColor;
    }
    public String getPhotoAuthorUserName() {
        return mPhotoAuthorUserName;
    }

    public void setPhotoAuthorUserName(String photoAuthorUserName) {
        mPhotoAuthorUserName = photoAuthorUserName;
    }

    public String getUserProfilePictureUrl() {
        return mUserProfilePictureUrl;
    }

    public void setUserProfilePictureUrl(String userProfilePictureUrl) {
        mUserProfilePictureUrl = userProfilePictureUrl;
    }

}
