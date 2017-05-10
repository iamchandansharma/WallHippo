package me.chandansharma.wallhippo.model;

/**
 * Created by iamcs on 2017-04-27.
 */

public class CollectionDetail {

    /**
     * All the member variable that use to store information
     */
    private String mCollectionId;
    private String mCollectionTitle;
    private String mCollectionAuthorProfilePictureUrl;
    private String mCollectionThumbnailUrl;
    private String mCollectionAuthorName;

    public CollectionDetail(String mCollectionId, String mCollectionTitle,
                            String mCollectionAuthorProfilePictureUrl,
                            String mCollectionThumbnailUrl,
                            String mCollectionAuthorName) {
        this.mCollectionId = mCollectionId;
        this.mCollectionTitle = mCollectionTitle;
        this.mCollectionAuthorProfilePictureUrl = mCollectionAuthorProfilePictureUrl;
        this.mCollectionThumbnailUrl = mCollectionThumbnailUrl;
        this.mCollectionAuthorName = mCollectionAuthorName;
    }

    public String getCollectionId() {
        return mCollectionId;
    }

    public void setCollectionId(String collectionId) {
        mCollectionId = collectionId;
    }

    public String getCollectionTitle() {
        return mCollectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        mCollectionTitle = collectionTitle;
    }

    public String getCollectionAuthorProfilePictureUrl() {
        return mCollectionAuthorProfilePictureUrl;
    }

    public void setCollectionAuthorProfilePictureUrl(String collectionAuthorProfilePictureUrl) {
        mCollectionAuthorProfilePictureUrl = collectionAuthorProfilePictureUrl;
    }

    public String getCollectionThumbnailUrl() {
        return mCollectionThumbnailUrl;
    }

    public void setCollectionThumbnailUrl(String collectionThumbnailUrl) {
        mCollectionThumbnailUrl = collectionThumbnailUrl;
    }

    public String getCollectionAuthorName() {
        return mCollectionAuthorName;
    }

    public void setCollectionAuthorName(String collectionAuthorName) {
        mCollectionAuthorName = collectionAuthorName;
    }
}
