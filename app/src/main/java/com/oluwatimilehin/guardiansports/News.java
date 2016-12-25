package com.oluwatimilehin.guardiansports;

/**
 * Created by timad on 25/12/2016.
 */

public class News {

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }

    protected String mTitle, mDate, mUrl;


    public News(String title, String date, String url){
        mDate = date;
        mTitle = title;
        mUrl = url;
    }
}
