package com.example.tripacker.tripacker;

import com.example.tripacker.tripacker.entity.Spot;

/**
 * Created by angelagao on 4/14/16.
 */
public interface SpotDetailsView extends LoadDataView {
    void renderSpot(Spot spot);
}
