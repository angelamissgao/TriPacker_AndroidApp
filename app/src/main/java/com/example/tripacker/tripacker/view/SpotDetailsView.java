package com.example.tripacker.tripacker.view;

import com.example.tripacker.tripacker.entity.Spot;
import com.example.tripacker.tripacker.view.LoadDataView;

/**
 * Created by angelagao on 4/14/16.
 */
public interface SpotDetailsView extends LoadDataView {
    void renderSpot(Spot spot);
}
