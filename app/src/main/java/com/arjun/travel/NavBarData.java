package com.arjun.travel;

import android.graphics.drawable.Drawable;

/**
 * Created by arjun on 9/10/15.
 */
public class NavBarData {

    /*private int iconId;
    private String iconUri;*/
    private String title;
    private String discription;

    public Drawable getThumb_d() {
        return thumb_d;
    }

    public void setThumb_d(Drawable thumb_d) {
        this.thumb_d = thumb_d;
    }

    private Drawable thumb_d;

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    /*public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
