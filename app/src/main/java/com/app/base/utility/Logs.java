package com.app.base.utility;

import android.util.Log;

/**
 * Created by j3p0n on 12/3/2016.
 */
public class Logs {

//    public static void x(final TextView tv, final String text) {
//        if (Config.DEBUG_MODE) {
//                    tv.setText(tv.getText().toString() +"\n"+text);
//            try {
//                Thread.sleep(1500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public static void d(String text) {
        if (Config.DEBUG_MODE) {
            Log.d(Config.LOG_TAG, "" + text);
        }
    }

    public static void d(int text) {
        if (Config.DEBUG_MODE) {
            Log.d(Config.LOG_TAG, String.valueOf(text));
        }
    }

    public static void e(String text) {
        if (Config.DEBUG_MODE) {
            Log.w("#" + Config.LOG_TAG + "#", "" + text);
        }
    }

    public static void d(String tag, String text) {
        if (Config.DEBUG_MODE) {
            Log.d(Config.LOG_TAG, "["+tag+"] ==> " + text);
        }
    }
}
