package com.example.flower.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.flower.constant.SharedPreferencesKey;


/**
 * @author ShenBen
 * @date 2018/11/16 16:18
 * @email 714081644@qq.com
 */
public class SharedPreferencesUtil {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private SharedPreferencesUtil() {

    }

    private static final class Holder {
        private static final SharedPreferencesUtil INSTANCE = new SharedPreferencesUtil();
    }

    public static SharedPreferencesUtil getInstance() {
        return Holder.INSTANCE;
    }

    @SuppressLint("CommitPrefEdits")
    public void init(Context context) {
        mPreferences = context.getApplicationContext()
                .getSharedPreferences(SharedPreferencesKey.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public void putFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.apply();
    }

    public void putLong(String key, long value) {
        mEditor.putFloat(key, value);
        mEditor.apply();
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

}
