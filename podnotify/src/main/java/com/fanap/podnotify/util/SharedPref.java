package com.fanap.podnotify.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arvin
 * on Mon, 24 December 2018 at 11:40 AM.
 * hi [at] arvinrokni [dot] ir
 */

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
