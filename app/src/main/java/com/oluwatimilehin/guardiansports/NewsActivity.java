package com.oluwatimilehin.guardiansports;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    NewsAdapter mAdapter;
    View progressBar;
    TextView emptyTextView;
    private final String QUERY_URL = "http://content.guardianapis.com/search?q=sports&section=football&order-by=newest&page-size=40&api-key=11331b2d-1fb0-4c5a-9b78-7f4fb5241551";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView list = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(mAdapter.getItem(position).getUrl()));
                startActivity(i);
            }
        });


        emptyTextView = (TextView) findViewById(R.id.empty_text_view);
        list.setEmptyView(emptyTextView);

        LoaderManager loaderManager = getLoaderManager();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(1, null, this);
        }
        else{
                progressBar = findViewById(R.id.loading_indicator);
                progressBar.setVisibility(View.GONE);
                emptyTextView.setText(R.string.no_network);
            }
        

    }


    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, QUERY_URL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<News>> loader, ArrayList<News> news) {
         mAdapter.clear();
         progressBar = findViewById(R.id.loading_indicator);
         progressBar.setVisibility(View.GONE);

        if(news != null){
            mAdapter.addAll(news);
        }

       emptyTextView.setText(R.string.empty_text_view);
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<News>> loader) {
        mAdapter.clear();
    }
}
