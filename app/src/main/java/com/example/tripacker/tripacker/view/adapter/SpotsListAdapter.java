package com.example.tripacker.tripacker.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
 * Created by angelagao on 5/2/16.
 */
public class SpotsListAdapter extends ArrayAdapter<SpotEntity> {
    public SpotsListAdapter(Context context, ArrayList<SpotEntity> spots) {
        super(context, 0, spots);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource((Resources) res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource((Resources) res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        SpotEntity spot = (SpotEntity) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spot_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tripspotDate = (TextView) convertView.findViewById(R.id.tripspot_date);
        TextView tripspotLocation = (TextView) convertView.findViewById(R.id.tripspot_location);
        TextView tripspotName = (TextView) convertView.findViewById(R.id.tripspot_name);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.spot_pic);

        //    // Populate the data into the template view using the data object
//        tripspotDate.setText(spot.getGmt_create());
        Log.i("TripSpotTimeline", spot.toString());
//        tripspotLocation.setText(spot.getCity_id());
        tripspotName.setText(spot.getName());
//        imageView.setBackgroundResource(spot.getImage_local());

        //Handle Out of Memory Error
        imageView.setImageBitmap(decodeSampledBitmapFromResource(
                getContext().getResources(), spot.getImage_local(),
                imageView.getWidth(), imageView.getHeight()));


        return convertView;
    }
}
