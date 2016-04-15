package com.example.tripacker.tripacker.view;

import com.example.tripacker.tripacker.entity.SpotEntity;

import java.util.ArrayList;

/**
 * Created by angelagao on 4/14/16.
 */
public interface SpotListView extends LoadDataView{

    void renderSpotList(ArrayList<SpotEntity> Spot);
}
