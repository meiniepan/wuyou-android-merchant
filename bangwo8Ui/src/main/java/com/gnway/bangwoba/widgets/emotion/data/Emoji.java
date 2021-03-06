package com.gnway.bangwoba.widgets.emotion.data;

import android.net.Uri;

/**
 * Created by Administrator on 2015/11/15.
 */
public class Emoji implements Emoticon {
    private int drawableResId;
    private String decInt;

    public Emoji(int drawableResId, String decInt) {
        this.drawableResId = drawableResId;
        this.decInt = decInt;
    }

    @Override
    public int getResourceId() {
        return drawableResId;
    }

    @Override
    public String getImagePath() {
        return null;
    }

    @Override
    public Uri getUri() {
        return null;
    }

    @Override
    public String getDesc() {
        return decInt;
    }

    @Override
    public EmoticonType getEmoticonType() {
        return EmoticonType.NORMAL;
    }
}
