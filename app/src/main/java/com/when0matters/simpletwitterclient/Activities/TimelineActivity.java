package com.when0matters.simpletwitterclient.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.when0matters.simpletwitterclient.Adapters.TweetsArrayAdapter;
import com.when0matters.simpletwitterclient.Global.SimpleTwitterClientApplication;
import com.when0matters.simpletwitterclient.Listeners.EndlessScrollListener;
import com.when0matters.simpletwitterclient.Models.Tweet;
import com.when0matters.simpletwitterclient.Models.User;
import com.when0matters.simpletwitterclient.R;
import com.when0matters.simpletwitterclient.RestApi.SimpleTwitterRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private SimpleTwitterRestClient client;
    private TweetsArrayAdapter tweetArrayAdapter;
    private List<Tweet> tweets;
    private ListView lv_tweets;

    private int numRecordsToLoad = 10;
    public static long max_id = 1;
    private Bundle bundle;
    private static User currentLoggedOnUser;
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        mContext = this;
        //finding the listview
        lv_tweets = (ListView) findViewById(R.id.lv_tweets);
        //create the data source
        tweets = new ArrayList<>();
        //create the adapter
        tweetArrayAdapter = new TweetsArrayAdapter(this, tweets);
        //connect adapter to listview
        lv_tweets.setAdapter(tweetArrayAdapter);
        lv_tweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore() {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                populateTimeline(numRecordsToLoad,max_id,false);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        //make client singleton throughout the entire application
        client = SimpleTwitterClientApplication.getRestClient();
        populateTimeline(numRecordsToLoad, max_id,true);
        initCurrentLoggedOnUser();

    }

    //Send an API request to get the timeline json
    //fill the listview by creating the tweets object from the json
    private void populateTimeline(int count, long max_id, boolean isFirstLoad) {
        // Send an API request to retrieve appropriate paginated data
        client.getHomeTimeline(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Debug", response.toString());
                //Deserialize json
                //Create model and add them to the adapter
                //Load the model data into listview
                tweets.addAll(Tweet.fromJSONArray(response));
                Log.d("Debug", tweets.toString());
                tweetArrayAdapter.notifyDataSetChanged();
                TimelineActivity.max_id = tweets.get(tweets.size()-1).getUid();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("Debug", errorResponse.toString());
            }
        },count,max_id,isFirstLoad);
    }

    private void initCurrentLoggedOnUser(){
        client.getCurrentLoggedOnUser(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                currentLoggedOnUser = User.fromJSON(response);
                bundle = new Bundle();
                bundle.putParcelable(TimelineActivity.mContext.getResources().getString(R.string.bundle_key_user), currentLoggedOnUser);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("Debug", errorResponse.toString());
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    CreateTweetFragment createTweetFragment;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                FragmentManager fragmentManager = getSupportFragmentManager();
                createTweetFragment = new CreateTweetFragment();
                createTweetFragment.setArguments(bundle);
                createTweetFragment.show(fragmentManager,null);
                break;
        }
        return true;
    }

    public void Dismiss(View view) {
        createTweetFragment.dismiss();
    }

}
