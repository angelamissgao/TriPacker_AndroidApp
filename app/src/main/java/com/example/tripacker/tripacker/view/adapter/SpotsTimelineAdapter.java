package com.example.tripacker.tripacker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;

import java.util.ArrayList;

/**
 * Created by angelagao on 4/11/16.
 */
public class SpotsTimelineAdapter extends ArrayAdapter<SpotEntity> {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public SpotsTimelineAdapter(Context context, ArrayList<SpotEntity> spots) {
        super(context, 0, spots);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpotEntity spot = (SpotEntity) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_layout, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.spot_item_img);
        TextView textView = (TextView) convertView.findViewById(R.id.spot_item_text);

//        imageView.setBackground(spot.getImage_main());
        textView.setText(spot.getName());
        return convertView;
    }
}
