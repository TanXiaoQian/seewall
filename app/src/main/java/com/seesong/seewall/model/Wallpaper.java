package com.seesong.seewall.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanxiaoqian on 2018/6/18.
 */

public class Wallpaper implements Parcelable {

    private String user;//图片所属
    private String src;
    private String title;//图片名称
    private String lickCount;//赞
    private String favoriteCount;//收藏
    private String commentCount;//评论

    protected Wallpaper(Parcel in) {
        user = in.readString();
        src = in.readString();
        title = in.readString();
        lickCount = in.readString();
        favoriteCount = in.readString();
        commentCount = in.readString();
    }

    public Wallpaper() {
    }

    public static final Creator<Wallpaper> CREATOR = new Creator<Wallpaper>() {
        @Override
        public Wallpaper createFromParcel(Parcel in) {
            return new Wallpaper(in);
        }

        @Override
        public Wallpaper[] newArray(int size) {
            return new Wallpaper[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getLickCount() {
        return lickCount;
    }

    public void setLickCount(String lickCount) {
        this.lickCount = lickCount;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(src);
        dest.writeString(title);
        dest.writeString(lickCount);
        dest.writeString(favoriteCount);
        dest.writeString(commentCount);
    }
}
