package com.when0matters.simpletwitterclient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.when0matters.simpletwitterclient.R;
import com.when0matters.simpletwitterclient.Models.Tweet;

import java.util.List;

/**
 * Created by dongdong on 3/13/2017.
 */

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context,android.R.layout.simple_list_item_1, tweets);
    }


    public View getView(int position, View convertView, ViewGroup parent){
        //1. get the tweet
        Tweet tweet = getItem(position);
        //2. Find or inflate the template
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent,false);
        }

        //3. Find subviews to fill with data in the template
        ImageView iv_profile_image = (ImageView) convertView.findViewById(R.id.iv_profile_image);
        TextView tv_user_name = (TextView) convertView.findViewById(R.id.tv_username);
        TextView tv_body = (TextView) convertView.findViewById(R.id.tv_Body);
        TextView tv_user = (TextView) convertView.findViewById(R.id.tv_user);
        TextView tv_timeago = (TextView) convertView.findViewById(R.id.tv_timeago);
        //4. Populate data into the subviews
        tv_user_name.setText(tweet.getUser().getName());
        tv_body.setText(tweet.getBody());
        iv_profile_image.setImageResource(android.R.color.transparent);
        tv_user.setText(tweet.getUser().getScreenName());
        tv_timeago.setText(tweet.getTimeAgo());
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUr()).into(iv_profile_image);

        //5. Return the view to be inserted into the list.
        return convertView;
    }

}
