package com.fanap.podnotify.util;

import android.os.Build;
import android.text.TextUtils;

import com.fanap.podnotify.model.Info;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 10:01 AM.
*/
public class InfoUtils {

    public static Info creator(){

        Info info = new Info();

        info.setBrand(getDeviceBrand());
        info.setOs("android");
        info.setSdkType(Info.SDKType.ANDROID);
        info.setModel(getDeviceModel());
        info.setVersion(currentVersion());
        info.setLat(0D);
        info.setLng(0D);

        //Todo: get real location

        return info;
    }

    public static String currentVersion(){
        double release=Double.parseDouble(Build.VERSION.RELEASE.replaceAll("(\\d+[.]\\d+)(.*)","$1"));
        return "v"+release+", API Level: "+Build.VERSION.SDK_INT;
    }

    private static String getDeviceModel() {
        String model = Build.MODEL;
        String manufacturer = Build.MANUFACTURER;
        if (model.startsWith(manufacturer)) {
            model = model.replace(manufacturer,"").trim();
        }
        return model;
    }

    private static String getDeviceBrand() {
        String manufacturer = Build.MANUFACTURER;
        return capitalize(manufacturer);
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }
}
