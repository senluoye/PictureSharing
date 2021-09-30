package com.wkh.picturesharingapplication.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 这个类用于存放全局数据
 */
public class PreferenceUtils {
    volatile static PreferenceUtils preferenceUtils;
    final SharedPreferences sharedPreferences;

    static final String PREFERENCES_NAME = "android-picture-share";
    static final String KEY_USER_ID = "id";
    static final String KEY_USERNAME = "name";
    static final String KEY_PASSWORD = "password";
    static final String KEY_TOKEN = "token";

    public static void init(Application applicationContext) {
        if (preferenceUtils == null)
        {
            synchronized (PreferenceUtils.class)
            {
                if (preferenceUtils == null)
                {
                    preferenceUtils = new PreferenceUtils(applicationContext);
                }
            }
        }
    }

    public static PreferenceUtils getInstance() {
        return preferenceUtils;
    }

    private PreferenceUtils(Application applicationContext) {
        sharedPreferences = applicationContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveUsername(String username) {
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public void savePassword(String password) {
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void saveUserId(String userId) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public void reset() {
        sharedPreferences.edit().remove(KEY_TOKEN).remove(KEY_USERNAME).remove(KEY_PASSWORD).apply();
    }
}
