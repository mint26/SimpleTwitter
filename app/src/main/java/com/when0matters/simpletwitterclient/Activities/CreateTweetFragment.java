package com.when0matters.simpletwitterclient.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.when0matters.simpletwitterclient.Models.User;
import com.when0matters.simpletwitterclient.R;


public class CreateTweetFragment extends AppCompatDialogFragment{

    private TextView tv_character_count;
    private TextView tv_user;
    private ImageView iv_user_profile_image;
    private EditText et_tweet_body;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_tweet, container, false);
        tv_user = (TextView) view.findViewById(R.id.tv_user);
        tv_character_count = (TextView) view.findViewById(R.id.tv_character_count);
        et_tweet_body = (EditText) view.findViewById(R.id.et_tweet_body);
        iv_user_profile_image = (ImageView) view.findViewById(R.id.iv_user_profile_image);
        Bundle bundle = this.getArguments();
        User user = bundle.getParcelable(this.getResources().getString(R.string.bundle_key_user));

        tv_user.setText(user.getScreenName());
        Picasso.with(getContext()).load(user.getProfileImageUr()).into(iv_user_profile_image);


        et_tweet_body.addTextChangedListener(mTextEditorWatcher);
        return view;
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int remainingCharacter = 150 - s.length();
            tv_character_count.setText(remainingCharacter + "");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
