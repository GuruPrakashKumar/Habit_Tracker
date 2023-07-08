package com.example.habbittrackerapp;

public class Option {
    private String title;
    private int iconResId;
    private Class<?> activityClass;

    public Option(String title, int iconResId, Class<?> activityClass) {
        this.title = title;
        this.iconResId = iconResId;
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResId() {
        return iconResId;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }
}

