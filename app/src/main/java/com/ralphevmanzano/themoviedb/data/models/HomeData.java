package com.ralphevmanzano.themoviedb.data.models;


public class HomeData {
    public static final int HEADER = 1;
    public static final int MOVIE_LIST = 2;

    private int viewType;
    private Object data;

    public HomeData(int viewType, Object data) {
        this.viewType = viewType;
        this.data = data;
    }

    public int getViewType() {
        return viewType;
    }

    public Object getData() {
        return data;
    }
}
