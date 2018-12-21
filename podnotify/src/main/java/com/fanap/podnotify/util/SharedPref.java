package com.fanap.podnotify.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static SharedPreferences instance = null;

    public static synchronized SharedPreferences getInstance(Context context) {

        if (instance == null){
            instance = context.getSharedPreferences(context.getApplicationContext().getPackageName(),
                    Context.MODE_PRIVATE);
        }

        return instance;
    }

    private SharedPref() {
    }
}
