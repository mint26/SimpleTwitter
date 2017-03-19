package com.when0matters.simpletwitterclient.RestApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 *
 * This is the object responsible for communicating with a REST API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes:
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 *
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 *
 */
public class SimpleTwitterRestClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "a2lXwtsBkdzh0q9eCNfiZgbdX";       // Change this
    public static final String REST_CONSUMER_SECRET = "txVNyCYJOoj6rdb0QKUNDAg1EYJEvlChDgh6HPM5dE943ituWC"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://simpletwittertweets"; // Change this (here and in manifest)

    public SimpleTwitterRestClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }

/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
 * 	  i.e getApiUrl("statuses/home_timeline.json");
 * 2. Define the parameters to pass to the request (query or body)
 *    i.e RequestParams params = new RequestParams("foo", "bar");
 * 3. Define the request method and make a call to the client
 *    i.e client.get(apiUrl, params, handler);
 *    i.e client.post(apiUrl, params, handler);
 */
    public void getHomeTimeline(AsyncHttpResponseHandler handler, int count, long max_id, boolean isFirstLoad){
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", count);
        params.put("since_id", 1);
        if (!isFirstLoad)
            params.put("max_id", max_id);
        client.get(apiUrl, params, handler);
    }

    public void getCurrentLoggedOnUser(AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("account/verify_credentials.json");
        client.get(apiUrl, handler);
    }

}

