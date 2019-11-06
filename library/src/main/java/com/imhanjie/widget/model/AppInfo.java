package com.imhanjie.widget.model;

import android.graphics.drawable.Drawable;

/**
 * Created by 95han on 2016/6/30.
 */
public class AppInfo {

    public String name;
    public transient Drawable icon;
    public String packageName;

    public AppInfo(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
    }

    public AppInfo(String name, Drawable icon, String packageName) {
        this.name = name;
        this.icon = icon;
        this.packageName = packageName;
    }

}
