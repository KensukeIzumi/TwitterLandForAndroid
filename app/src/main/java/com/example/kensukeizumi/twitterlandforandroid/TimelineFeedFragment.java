package com.example.kensukeizumi.twitterlandforandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class TimelineFeedFragment extends Fragment implements TimelineFeedAdapter.postOnClickHandler{
    private View mView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Toast mToast;
    private Context mContext;

    public TimelineFeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_timeline_feed, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.timeline_feed_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        String[] posts = {"AAA", "BBB", "CCC"};

        mAdapter = new TimelineFeedAdapter(this, posts);
        mRecyclerView.setAdapter(mAdapter);

        return mView;
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
