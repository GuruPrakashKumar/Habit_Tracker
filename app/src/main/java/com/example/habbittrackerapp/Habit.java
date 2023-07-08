package com.example.habbittrackerapp;

public class Habit {
    private int id;
    private String name;
    private String description;
    private int iconResId;
    private boolean selected;

    int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Habit(int id, String name, String description, int iconResId, boolean selected, int progress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconResId = iconResId;
        this.selected = selected;
        this.progress = progress;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
