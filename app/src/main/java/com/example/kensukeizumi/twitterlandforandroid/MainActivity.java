package com.example.kensukeizumi.twitterlandforandroid;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toast mToast;
    private TimelineFeedFragment mTimelineFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu_main);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.reload) {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(MainActivity.this, "reload is clicked", Toast.LENGTH_LONG);
                    mToast.show();
                    mTimelineFeedFragment.loadTimelinePosts();
                }

                if (id == R.id.move_to_post) {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(MainActivity.this, "move to post is clicked", Toast.LENGTH_LONG);
                    mToast.show();
                }
                return true;
            }
        });

        if (!TwitterUtils.hasAccessToken(this)) {
            Intent intent = new Intent(this, TwitterOAuthActivity.class);
            startActivity(intent);
            finish();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mTimelineFeedFragment = new TimelineFeedFragment();
        fragmentTransaction.add(R.id.container, mTimelineFeedFragment);
        fragmentTransaction.commit();
    }
}
