package com.corporateapp.AppUtils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bitware on 16/10/17.
 */

public class AppSharedPreference {

    public static void putValue(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("pcmc_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("pcmc_pref", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void clearPreference(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("pcmc_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
