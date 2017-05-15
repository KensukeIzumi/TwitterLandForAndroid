package com.example.kensukeizumi.twitterlandforandroid;
import android.content.Context;
import android.content.SharedPreferences;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * Created by kensukeizumi on 2017/05/14.
 */

public class TwitterUtils {

    private static final String TOKEN = "token";
    private static final String TOKEN_SECRET = "token_secret";
    private static final String PREF_TOKEN = "twitter_token_access";

    public static void storeAccessToken(Context context, AccessToken accessToken) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(TOKEN, accessToken.getToken());
        editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
        editor.commit();
    }

    public static Twitter getTwitterInstance(Context context) {
        String consumerKey = context.getString(R.string.consumer_key);
        String consumerKeySecret = context.getString(R.string.consumer_key_secret);

        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerKeySecret);

        AccessToken accessToken = loadAccessToken(context);

        if (accessToken != null) {
            twitter.setOAuthAccessToken(accessToken);
        }
        return twitter;
    }

    public static AccessToken loadAccessToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_TOKEN, Context.MODE_PRIVATE);
        String token = preferences.getString(TOKEN, null);
        String token_secret = preferences.getString(TOKEN_SECRET, null);
        if (token != null && token_secret != null) {
            return new AccessToken(token, token_secret);
        }
        return null;
    }

    public static boolean hasAccessToken(Context context) {
        return loadAccessToken(context) != null;
    }
}
