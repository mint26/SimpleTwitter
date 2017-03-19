package com.when0matters.simpletwitterclient.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by dongdong on 3/13/2017.
 */

//1) Parse the JSON + store the data, enscapsultes logic or display logic
public class Tweet{
    //list out attribute
    private String body;
    private long uid; //unique id for the tweet
    private User user;
    private String createdAt;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTimeAgo(){
        long difference = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date tweetCreatedDate = simpleDateFormat.parse(this.getCreatedAt());
            Date currentDate = getCurrentDate(TimeZone.getTimeZone("GMT"));
            difference = Math.abs(tweetCreatedDate.getTime() - currentDate.getTime())/ (1000);

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
            if (difference > 60 * 60 * 24) {
                difference = difference / (60 * 60 * 24);
                return difference + "D";
            }
            else if (difference > 60 * 60){
                difference = difference / (60 * 60);
                return difference + "H";
            }
            else if (difference >  60){
                difference = difference / 60;
                return difference + "m";
            }
            else{
                return difference + "s";
            }
    }

    public User getUser() {
        return user;
    }

    //Deserialize the JSON
    //Tweet.fromJSON...
    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        //Extract the values from the json, stores them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<>();
        //Iterate the json array and create tweets
        for (int i = 0; i <jsonArray.length(); i++){
            try{
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null){
                    tweets.add(tweet);
                }

            }catch (JSONException ex){
                ex.printStackTrace();
                //continue process if one tweet fail
                continue;
            }
        }

        //return the finished list
        return tweets;
    }

    public Date getCurrentDate(TimeZone timeZone){
        Calendar cal = Calendar.getInstance(timeZone);
        return cal.getTime();

    }
}
