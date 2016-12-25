package com.oluwatimilehin.guardiansports;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by timad on 25/12/2016.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    protected String mUrl;
    public NewsLoader(Context context, String url ){
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        ArrayList<News> news = QueryUtils.extractNews(mUrl);
        return news;
    }
}
