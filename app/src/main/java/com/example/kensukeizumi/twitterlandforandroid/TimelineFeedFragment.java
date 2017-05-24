package com.example.kensukeizumi.twitterlandforandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TimelineFeedFragment extends Fragment implements TimelineFeedAdapter.postOnClickHandler{
    private View mView;
    private RecyclerView mRecyclerView;
    private TimelineFeedAdapter mAdapter;
    private Toast mToast;
    private Context mContext;
    private Twitter mTwitter;
    private ArrayList<String> mPosts;

    public TimelineFeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTwitter = TwitterUtils.getTwitterInstance(mContext);

        mView = inflater.inflate(R.layout.fragment_timeline_feed, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.timeline_feed_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new TimelineFeedAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        loadTimelinePosts();

        return mView;
    }

    public void loadTimelinePosts() {
        new FetchPostsTask().execute();
    }

    public class FetchPostsTask extends AsyncTask<Void, Void, ArrayList<Status>> {
        @Override
        protected ArrayList<twitter4j.Status> doInBackground(Void... params) {
            try {
                ArrayList<twitter4j.Status> list = new ArrayList<>();
                ResponseList<twitter4j.Status> timeline = mTwitter.getHomeTimeline();
                for (twitter4j.Status post : timeline) {
                    list.add(post);
                }
                return list;
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<twitter4j.Status> arrayList) {
            if (arrayList != null) {
                mAdapter.setTimelinePosts(arrayList);
            } else {
                Toast.makeText(mContext, "failed to fetch timeline", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void OnClickHandler(String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        String message = text + " is clicked";
        mToast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

}
