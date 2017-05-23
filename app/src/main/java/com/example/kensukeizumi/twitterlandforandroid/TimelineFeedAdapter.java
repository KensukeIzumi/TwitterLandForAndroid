package com.example.kensukeizumi.twitterlandforandroid;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

import twitter4j.Status;
import twitter4j.User;

/**
 * Created by kensukeizumi on 2017/05/20.
 */

public class TimelineFeedAdapter extends RecyclerView.Adapter<TimelineFeedAdapter.TimelineFeedAdapterViewHolder> {
    private ArrayList<Status> mPosts = new ArrayList<Status>();
    private Context mContext;
    final private TimelineFeedAdapter.postOnClickHandler mListener;

    public interface postOnClickHandler {
        void OnClickHandler(String text);
    }

    public TimelineFeedAdapter(TimelineFeedAdapter.postOnClickHandler postOnClickHandler) {
        mListener = postOnClickHandler;
    }

    public class TimelineFeedAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mIconImageView;
        public final TextView mUserNameTextView;
        public final TextView mUserScreenNameTextView;
        public final TextView mPostTextView;

        public TimelineFeedAdapterViewHolder(View view) {
            super(view);
            mIconImageView = (ImageView) view.findViewById(R.id.icon);
            mUserNameTextView = (TextView) view.findViewById(R.id.user_name);
            mUserScreenNameTextView = (TextView) view.findViewById(R.id.user_screen_name);
            mPostTextView = (TextView) view.findViewById(R.id.tweet_text);
            mPostTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mListener.OnClickHandler(mPosts.get(adapterPosition).getText());
        }
    }

    @Override
    public TimelineFeedAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_tweet, parent, false);

        return new TimelineFeedAdapterViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mPosts == null) {
            return 0;
        }

        return mPosts.size();
    }

    @Override
    public void onBindViewHolder(TimelineFeedAdapterViewHolder holder, int position) {
        Status post = mPosts.get(position);
        User user = post.getUser();
        holder.mUserNameTextView.setText(user.getName());
        holder.mUserScreenNameTextView.setText(user.getScreenName());
        holder.mPostTextView.setText(post.getText());

        Uri uri = Uri.parse(user.getProfileImageURL());
        Picasso.with(mContext).load(uri).into(holder.mIconImageView);
    }

    public void setTimelinePosts(ArrayList<Status> tweets) {
        mPosts = tweets;
        notifyDataSetChanged();
    }
}
