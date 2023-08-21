package com.example.cleancodeofledger.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtils {
    static SharedPreferences pref = null;

    public static void saveLanguageChoice(String languageCode){
        pref = PreferenceManager.getDefaultSharedPreferences(AllData.context);
        pref.edit().putString("chosen_language", languageCode).apply();
    }

    public static String getLanguageChoice(Context context){
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString("chosen_language", "zh-TW");
    }
}
