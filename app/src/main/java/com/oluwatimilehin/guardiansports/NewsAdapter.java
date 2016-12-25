package com.oluwatimilehin.guardiansports;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by timad on 25/12/2016.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(NewsActivity context, ArrayList<News> news){
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }
        News currentNews = getItem(position);

        TextView headlineTextView = (TextView) listItemView.findViewById(R.id.headline);
        headlineTextView.setText(currentNews.getTitle());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);

        String dateString = currentNews.getDate();
        String[] dateSplit = dateString.split("T");
        String dateStart = dateSplit[0];
        String[] secondDateSplit = dateSplit[1].split("Z");
        String dateEnd = secondDateSplit[0];
        String date = dateStart + " " +dateEnd ;

        dateTextView.setText(date);

        return  listItemView;
    }
}
