package com.fanap.podnotify.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ArvinRokni
 * on Sun, 09 December 2018 at 5:57 PM.
*/
public abstract class AsyncConst {

    private final String value;

    public AsyncConst(@AsyncConst.Constants String value) {
        this.value = value;
    }


    @StringDef({
            Constants.ASYNC_READY,
            Constants.OPEN

    })

    @Retention(RetentionPolicy.SOURCE)
    public @interface Constants {

        String ASYNC_READY = "ASYNC_READY";
        String OPEN = "OPEN";
    }
}
