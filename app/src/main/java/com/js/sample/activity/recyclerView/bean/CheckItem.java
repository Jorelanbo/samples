package com.js.sample.activity.recyclerView.bean;

// Created by JS on 2020/4/11.

public class CheckItem {
    private String name;
    private boolean isChecked;

    public CheckItem() {
    }

    public CheckItem(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "CheckItem{" +
                "name='" + name + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
