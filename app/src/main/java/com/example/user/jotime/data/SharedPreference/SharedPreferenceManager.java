package com.example.user.jotime.data.SharedPreference;


import android.content.Context;
import android.content.SharedPreferences;

import static com.example.user.jotime.AppConstans.APP_PREFERENCES;
import static com.example.user.jotime.AppConstans.KEY_ID_PREFERENCES;

public class SharedPreferenceManager {

    private SharedPreferenceManager(){
        //empty
    }

    public static void saveId(int id, Context context){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID_PREFERENCES, id);
        editor.apply();
    }

    public static int getId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID_PREFERENCES, 0);
    }
}
