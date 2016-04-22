package com.example.tripacker.tripacker.ws.remote;


public class TripPackerAPIs {

    final static String API_BASE_URL = WebServices.getBaseUrl();


    public static String loginUser(){
        return API_BASE_URL+"/member/login/dologin";
    }
    // Get User

    public static String getUserDetail(int user_id){
        return API_BASE_URL+"/users/"+user_id;
    }

    // Get Trip

    public static String getPopularTrips(){
        return API_BASE_URL+"/trips/polular";
    }

    public static String getPopularTripsByPage(){
        return API_BASE_URL+"/trips/polular";
    }

    public static String getTripDetail(int trip_id){
        return API_BASE_URL+"/trips/"+trip_id;
    }

    // Get Spot

    public static String getSpotsList() {return API_BASE_URL + "/spot/getspots";}

    public static String getPopularSpots(){
        return API_BASE_URL+"/spots/polular";
    }

    public static String getPopularSpotsByPage(){
        return API_BASE_URL+"/spots/polular";
    }

    public static String getSpotDetail(int spot_id){
        return API_BASE_URL+"/spots/"+spot_id;

    }


    // Get configuration

    public String getConfiguration(){
        return API_BASE_URL+"/configuration";
    }

}
