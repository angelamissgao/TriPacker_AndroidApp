package com.example.tripacker.tripacker.ws.remote;


public class TripPackerAPIs {

    final static String API_BASE_URL = WebServices.getBaseUrl();


    public static String loginUser(){
        return API_BASE_URL+"/member/login";
    }
    // Get User

    public static String getUserProfile(int user_id){
        return API_BASE_URL+"/member/profile/getprofile";
    }
    public static String updateUserProfile(int user_id){
        return API_BASE_URL+"/member/"+user_id+"/profile";
    }

    // Get Trip

    public static String createTrip() { return API_BASE_URL+"/trip/create"; }

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

    public static String getSpotsList(String id) {return API_BASE_URL + "/spot/getByCity/" + id;}

    public static String createSpot() {return API_BASE_URL + "/spot/create";}

    public static String editSpot(String spotId) {return API_BASE_URL + "/spot/" + spotId;}

    public static String getPopularSpots(){
        return API_BASE_URL+"/spots/polular";
    }

    public static String getPopularSpotsByPage(){
        return API_BASE_URL+"/spots/polular";
    }

    public static String getSpotDetail(String spotId){return API_BASE_URL+"/spot/" + spotId;}


    // Get configuration

    public String getConfiguration(){
        return API_BASE_URL+"/configuration";
    }

}
