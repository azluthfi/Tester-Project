package com.app.basarnas.utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;
import static com.app.basarnas.utility.Config.AUTH_KEY;

/**
 * Created by j3p0n on 12/3/2016.
 */
public final class CommonUtilities {

    // Google project id
    public static final String SENDER_ID = "1096616991132";
    public static final String YT_DEVELOPER_KEY = "AIzaSyBzDp0LsSdK-bmJT-m1gwLViROu2LHfPdo";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    public static String get_msisdn(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        // String mPhoneNumber = tMgr.getLine1Number();
        String mPhoneNumber = tMgr.getSubscriberId();
        return mPhoneNumber;
    }

    /**
     * Get Device Name / Model
     */

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model + " " + Build.PRODUCT;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * Checking for all possible internet providers
     * *
     */
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED
                            || info[i].isConnectedOrConnecting()) {
                        return true;
                    }

        }
        return false;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
//	        Log.d("PACKAGE STATUS", "INSTALLED" + packagename);
            return true;
        } catch (NameNotFoundException e) {
//	    	Log.d("PACKAGE STATUS", "NOT INSTALLED" + packagename);
            return false;
        }
    }


    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), HIDE_NOT_ALWAYS);
            }
        }
    }

    public static boolean isTimeBetweenTwoTime(String initialTime, String finalTime, String currentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        if (initialTime.matches(reg) && finalTime.matches(reg) && currentTime.matches(reg)) {
            boolean valid = false;
            //Start Time
            Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);

            //Current Time
            Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);

            //End Time
            Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);

            if (finalTime.compareTo(initialTime) < 0) {
                calendar2.add(Calendar.DATE, 1);
                calendar3.add(Calendar.DATE, 1);
            }

            Date actualTime = calendar3.getTime();
            if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0)
                    && actualTime.before(calendar2.getTime())) {
                valid = true;
            }
            return valid;
        } else {
            throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
        }

    }

    public static String toRupiahFormat(int nominal) {
        return toRupiahFormat(String.valueOf(nominal));
    }

    public static String toRupiahFormat(String nominal) {
        String rupiah = "";

        Locale locale = new Locale("id", "ID");
        DecimalFormat rupiahFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols symbols = rupiahFormat.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(""); // Don't use null.
        rupiahFormat.setDecimalFormatSymbols(symbols);

        rupiah = rupiahFormat.format(Double.parseDouble(nominal));

        return "Rp " + rupiah + ",00";
    }

    public static Spanned set_error(String error) {
        return toHtml("<font color='red'>" + error + "</font>");
    }

    public static Spanned toHtml(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }

    /**
     * @param strDate
     * @param sourceFormate
     * @param destinyFormate
     * @return --- Formate Mas DAP!!! ---
     * <table BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
     * <tr BGCOLOR="#2e2e2e" CLASS="TableHeadingColor">
     * <td><B>Symbol</B></td> <td><B>Meaning</B></td> <td><B>Kind</B></td> <td><B>Example</B></td> </tr>
     * <tr> <td>{@code D}</td> <td>day in year</td>             <td>(Number)</td>      <td>189</td> </tr>
     * <tr> <td>{@code E}</td> <td>day of week</td>             <td>(Text)</td>        <td>{@code E}/{@code EE}/{@code EEE}:Tue, {@code EEEE}:Tuesday, {@code EEEEE}:T</td> </tr>
     * <tr> <td>{@code F}</td> <td>day of week in month</td>    <td>(Number)</td>      <td>2 <i>(2nd Wed in July)</i></td> </tr>
     * <tr> <td>{@code G}</td> <td>era designator</td>          <td>(Text)</td>        <td>AD</td> </tr>
     * <tr> <td>{@code H}</td> <td>hour in day (0-23)</td>      <td>(Number)</td>      <td>0</td> </tr>
     * <tr> <td>{@code K}</td> <td>hour in am/pm (0-11)</td>    <td>(Number)</td>      <td>0</td> </tr>
     * <tr> <td>{@code L}</td> <td>stand-alone month</td>       <td>(Text)</td>        <td>{@code L}:1 {@code LL}:01 {@code LLL}:Jan {@code LLLL}:January {@code LLLLL}:J</td> </tr>
     * <tr> <td>{@code M}</td> <td>month in year</td>           <td>(Text)</td>        <td>{@code M}:1 {@code MM}:01 {@code MMM}:Jan {@code MMMM}:January {@code MMMMM}:J</td> </tr>
     * <tr> <td>{@code S}</td> <td>fractional seconds</td>      <td>(Number)</td>      <td>978</td> </tr>
     * <tr> <td>{@code W}</td> <td>week in month</td>           <td>(Number)</td>      <td>2</td> </tr>
     * <tr> <td>{@code Z}</td> <td>time zone (RFC 822)</td>     <td>(Time Zone)</td>   <td>{@code Z}/{@code ZZ}/{@code ZZZ}:-0800 {@code ZZZZ}:GMT-08:00 {@code ZZZZZ}:-08:00</td> </tr>
     * <tr> <td>{@code a}</td> <td>am/pm marker</td>            <td>(Text)</td>        <td>PM</td> </tr>
     * <tr> <td>{@code c}</td> <td>stand-alone day of week</td> <td>(Text)</td>        <td>{@code c}/{@code cc}/{@code ccc}:Tue, {@code cccc}:Tuesday, {@code ccccc}:T</td> </tr>
     * <tr> <td>{@code d}</td> <td>day in month</td>            <td>(Number)</td>      <td>10</td> </tr>
     * <tr> <td>{@code h}</td> <td>hour in am/pm (1-12)</td>    <td>(Number)</td>      <td>12</td> </tr>
     * <tr> <td>{@code k}</td> <td>hour in day (1-24)</td>      <td>(Number)</td>      <td>24</td> </tr>
     * <tr> <td>{@code m}</td> <td>minute in hour</td>          <td>(Number)</td>      <td>30</td> </tr>
     * <tr> <td>{@code s}</td> <td>second in minute</td>        <td>(Number)</td>      <td>55</td> </tr>
     * <tr> <td>{@code w}</td> <td>week in year</td>            <td>(Number)</td>      <td>27</td> </tr>
     * <tr> <td>{@code y}</td> <td>year</td>                    <td>(Number)</td>      <td>{@code yy}:10 {@code y}/{@code yyy}/{@code yyyy}:2010</td> </tr>
     * <tr> <td>{@code z}</td> <td>time zone</td>               <td>(Time Zone)</td>   <td>{@code z}/{@code zz}/{@code zzz}:PST {@code zzzz}:Pacific Standard Time</td> </tr>
     * <tr> <td>{@code '}</td> <td>escape for text</td>         <td>(Delimiter)</td>   <td>{@code 'Date='}:Date=</td> </tr>
     * <tr> <td>{@code ''}</td> <td>single quote</td>           <td>(Literal)</td>     <td>{@code 'o''clock'}:o'clock</td> </tr>
     * </table>
     */

    public static String getFormatedDate(String strDate, String sourceFormate, String destinyFormate) {
        SimpleDateFormat df;
        df = new SimpleDateFormat(sourceFormate);
        Date date = null;
        try {
            date = df.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        df = new SimpleDateFormat(destinyFormate);
        return df.format(date);

    }

    public static String difTime(String departTime, String arriveTime, String format) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse(departTime);
            date2 = simpleDateFormat.parse(arriveTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = date2.getTime() - date1.getTime();
        String returnString = "";
        if ((difference / (24 * 60 * 60 * 1000)) != 0) {
            returnString += String.valueOf(difference / (24 * 60 * 60 * 1000)) + "d ";
        }
        if ((difference / (60 * 60 * 1000) % 24) != 0) {
            returnString += String.valueOf(difference / (60 * 60 * 1000) % 24) + "h ";
        }
        if ((difference / (60 * 1000) % 60) != 0) {
            returnString += String.valueOf(difference / (60 * 1000) % 60) + "m ";
        }
        return returnString;
//        long diffSeconds = diff / 1000 % 60;
//        long diffMinutes = diff / (60 * 1000) % 60;
//        long diffHours = diff / (60 * 60 * 1000) % 24;
//        long diffDays = diff / (24 * 60 * 60 * 1000);

    }

    public static String loadJSONFromAsset(Context context, String fName) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(fName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public static SweetAlertDialog buildAlert(Context c, int alertType, String title, String content, String confirmLabel, String cancelLabel) {
        SweetAlertDialog alert = new SweetAlertDialog(c, alertType);
        if (title != null) {
            alert.setTitleText(title);
        }
        if (content != null) {
            alert.setContentText(content);
        }
        if (confirmLabel != null) {
            alert.setConfirmText(confirmLabel);
        }
        if (cancelLabel != null) {
            alert.setCancelText(cancelLabel);
        }
        alert.show();
        return alert;
    }

    public static String secure(String method, String pin) {
        if(pin.length() == 32){
            return md5(AUTH_KEY + md5(pin + method));
        }
        return md5(AUTH_KEY + md5(md5(md5(pin)) + method));
    }

    public static int toPlain(String priceProduk) {
        Pattern intsOnly = Pattern.compile("\\d+");
        Matcher makeMatch = intsOnly.matcher(priceProduk);
        makeMatch.find();
//        String result = makeMatch.group();
        return Integer.parseInt(priceProduk.replaceAll("\\D+","").trim());
    }


    public static <T> T getObject(String jsonString, Class<T> object) {
        return new Gson().fromJson(jsonString, object);
    }

    public static String getThumbs(String img) {
        return img.replace("media/image","thumbs");
    }

//    @NonNull
//    public static String buildAddress(CustomerShipping item) {
//        return item.getShippingAddress() + "\n"
//                + item.getShippingCityName() + ", "
//                + item.getShippingProvinceName() + " "
//                + item.getShippingZipCode() + "\n" + item.getShippingPhone();
//    }
}