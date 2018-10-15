package com.burhanloey.waktusolat.services.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Helper class to store/retrieve data to/from SharedPreferences.
 */
public class LocalStorage {
    private final static String STORAGE_NAME = "com.burhanloey.waktusolat.Prefs";

    private final Context context;

    public LocalStorage(Context context) {
        this.context = context;
    }

    public int getInt(String key, int defValue) {
        SharedPreferences preferences = context
                .getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);

        return preferences.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = context
                .getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
                .edit();

        editor.putInt(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        SharedPreferences preferences = context
                .getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);

        return preferences.getBoolean(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = context
                .getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
                .edit();

        editor.putBoolean(key, value);
        editor.apply();
    }
}
