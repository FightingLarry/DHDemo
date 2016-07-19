package com.dh.demo;

/**
 * Created by yancai.liu on 2016/7/8.
 */

public class MainModel {
    public enum Type {
        SystemUIIfLauncher,LauncherContentProvider
    }

    private Type type;
    private String title;
    private String des;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
