package com.example.kensukeizumi.twitterlandforandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kensukeizumi on 2017/05/20.
 */

public class TimelineFeedAdapter extends RecyclerView.Adapter<TimelineFeedAdapter.TimelineFeedAdapterViewHolder> {
    private String[] mPosts;
    final private TimelineFeedAdapter.postOnClickHandler mListener;

    public interface postOnClickHandler {
        void OnClickHandler(String text);
    }

    public TimelineFeedAdapter(TimelineFeedAdapter.postOnClickHandler postOnClickHandler, String[] posts) {
        mPosts = posts;
        mListener = postOnClickHandler;
    }

    public class TimelineFeedAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mPostTextView;

        public TimelineFeedAdapterViewHolder(View view) {
            super(view);
            mPostTextView = (TextView) view.findViewById(R.id.post_item_data);
            mPostTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mListener.OnClickHandler(mPosts[adapterPosition]);
        }
    }


    @Override
    public TimelineFeedAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.post_list_item, parent, false);

        return new TimelineFeedAdapterViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mPosts == null) {
            return 0;
        }

        return mPosts.length;
    }

    @Override
    public void onBindViewHolder(TimelineFeedAdapterViewHolder holder, int position) {
        holder.mPostTextView.setText(mPosts[position]);
    }
}
