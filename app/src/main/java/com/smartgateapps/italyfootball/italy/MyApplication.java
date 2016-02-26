package com.smartgateapps.italyfootball.italy;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.smartgateapps.italyfootball.R;
import com.smartgateapps.italyfootball.model.Legue;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Raafat on 04/11/2015.
 */
public class MyApplication extends Application {

    public static AlarmManager alarmManager;
    public static NotificationManager notificationManager;
    public static SharedPreferences pref;
    public static DbHelper dbHelper;
    public static SQLiteDatabase dbw, dbr;

    public static int pageSize = 15;
    public static Typeface font;

    public static final String BASE_URL = "http://m.kooora.com/";
    public static final String ITALY_EXT_HOME = "?n=0&o=ncit&pg=";
    public static final String ITALY_LEAGUE_EXT = "?c=7698";
    public static final String ITALY_CUP_EXT = "?c=11332";
    public static final String ITALY_LEAGUE_NEWS_EXT = "?n=0&o=n7698&pg=";
    public static final String ITALY_CUP_NEWS_EXT = "?n=0&o=n11332&pg=";
    public static final String TEAM_NEWS_EXT = "?n=0&o=n1000000";
    public static final String TEAM_MATCHES_EXT = "?region=-6&team=";

    public static final String TEAMS_CM = "&cm=t";
    public static final String POSES_CM = "&cm=i";
    public static final String MATCHES_CM = "&cm=m";
    public static final String SCORERS_CM = "&scorers=true";

    public static Context APP_CTX;
    public static final String LIVE_CAST_APP_PACKAGE_NAME = "com.smartgateapps.livesports";

    public static Picasso picasso;
    public static WebView webView;

    public static final int HEADER_TYPE_GOALERS = 0;

    public static String[] PLAYERS_POS = new String[]{"", "مدرب", "حارس", "دفاع", "وسط", "هجوم", "مساعد مدرب", " مدرب حراس", "مدرب بدني", "طبيب الفريق"};

    public static HashMap<String, Integer> monthOfTheYear = new HashMap<>(12);
    public static MyApplication instance;
    public static HashMap<Integer, Integer> teamsLogos = new HashMap<>();

    public static SimpleDateFormat sourceTimeFormate = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat destTimeFormate = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat sourceDateFormat = new SimpleDateFormat("E d MMMM yyy", new Locale("ar"));
    public static SimpleDateFormat destDateFormat = new SimpleDateFormat("E d MMMM yyy", new Locale("ar"));

    public static TimeZone currentTimeZone;

    public static InterstitialAd mInterstitialAd;


    public static Long parseDateTime(String date, String time) {

        Long dateL = 0L;
        Long timeL = MyApplication.getCurretnDateTime();
        try {
            dateL = sourceDateFormat.parse(date).getTime();
            timeL = sourceTimeFormate.parse(time).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateL + timeL - getCurrentOffset();
    }

    public static String[] formatDateTime(Long dateTime) {
        dateTime += getCurrentOffset();
        String date = sourceDateFormat.format(dateTime);
        String time = sourceTimeFormate.format(dateTime);

        return new String[]{date, time};
    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

        currentTimeZone = TimeZone.getDefault();
        sourceTimeFormate.setTimeZone(TimeZone.getTimeZone("UTC"));
        destTimeFormate.setTimeZone(currentTimeZone);
        sourceDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        destDateFormat.setTimeZone(currentTimeZone);

        teamsLogos.put(1177, R.mipmap.t1177);
        teamsLogos.put(1178, R.mipmap.t1178);
        teamsLogos.put(16422, R.mipmap.t16422);
        teamsLogos.put(16424, R.mipmap.t16424);
        teamsLogos.put(18056, R.mipmap.t18056);
        teamsLogos.put(19910, R.mipmap.t19910);
        teamsLogos.put(19911, R.mipmap.t19911);
        teamsLogos.put(21, R.mipmap.t21);
        teamsLogos.put(22, R.mipmap.t22);
        teamsLogos.put(23, R.mipmap.t23);
        teamsLogos.put(23027, R.mipmap.t23027);
        teamsLogos.put(24, R.mipmap.t24);
        teamsLogos.put(26, R.mipmap.t26);
        teamsLogos.put(27, R.mipmap.t27);
        teamsLogos.put(28, R.mipmap.t28);
        teamsLogos.put(2852, R.mipmap.t2852);
        teamsLogos.put(31, R.mipmap.t31);
        teamsLogos.put(32, R.mipmap.t32);
        teamsLogos.put(3201, R.mipmap.t3201);
        teamsLogos.put(3208, R.mipmap.t3208);
        teamsLogos.put(3216, R.mipmap.t3216);
        teamsLogos.put(3217, R.mipmap.t3217);
        teamsLogos.put(3350, R.mipmap.t3350);
        teamsLogos.put(3390, R.mipmap.t3390);
        teamsLogos.put(34, R.mipmap.t34);
        teamsLogos.put(3523, R.mipmap.t3523);
        teamsLogos.put(36, R.mipmap.t36);
        teamsLogos.put(3778, R.mipmap.t3778);
        teamsLogos.put(39, R.mipmap.t39);
        teamsLogos.put(40, R.mipmap.t40);
        teamsLogos.put(41, R.mipmap.t41);
        teamsLogos.put(5759, R.mipmap.t5759);
        teamsLogos.put(9420, R.mipmap.t9420);
        teamsLogos.put(9544, R.mipmap.t9544);
        teamsLogos.put(950, R.mipmap.t950);
        teamsLogos.put(951, R.mipmap.t951);
        teamsLogos.put(952, R.mipmap.t952);
        teamsLogos.put(9544, R.mipmap.t9544);
        teamsLogos.put(963, R.mipmap.t963);
        teamsLogos.put(964, R.mipmap.t964);
        teamsLogos.put(965, R.mipmap.t965);
        teamsLogos.put(967, R.mipmap.t967);
        teamsLogos.put(968, R.mipmap.t968);
        teamsLogos.put(972, R.mipmap.t972);
        teamsLogos.put(973, R.mipmap.t973);
        teamsLogos.put(975, R.mipmap.t975);
        teamsLogos.put(976, R.mipmap.t976);
        teamsLogos.put(977, R.mipmap.t985);
        teamsLogos.put(985, R.mipmap.t985);
        teamsLogos.put(990, R.mipmap.t990);
        teamsLogos.put(9918, R.mipmap.t9918);
        teamsLogos.put(996, R.mipmap.t996);
        teamsLogos.put(998, R.mipmap.t998);

        APP_CTX = getApplicationContext();
        font = Typeface.createFromAsset(APP_CTX.getAssets(), "fonts/jf_flat_regular.ttf");
        dbHelper = new DbHelper(APP_CTX);
        dbw = dbHelper.getWritableDatabase();
        dbr = dbHelper.getReadableDatabase();

        picasso = Picasso.with(this);

        Legue italy = new Legue(0L, "أخبار إيطاليا", "?y=IT", ITALY_EXT_HOME);
        Legue ilalyLeague = new Legue(1L, "الدوري الإيطالي الدرجة A", ITALY_LEAGUE_EXT, ITALY_LEAGUE_NEWS_EXT);
        Legue italyCup = new Legue(2L, "كأس إيطاليا", ITALY_CUP_EXT, ITALY_CUP_NEWS_EXT);

        italy.save();
        ilalyLeague.save();
        italyCup.save();

        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.APP_CTX);
        boolean b = pref.getBoolean(getString(R.string.italy_league_notificatin_pref_key), true);
        pref.edit().putBoolean(getString(R.string.italy_league_notificatin_pref_key), b).apply();
        notificationManager = (NotificationManager) APP_CTX.getSystemService(NOTIFICATION_SERVICE);

        alarmManager = (AlarmManager) APP_CTX.getSystemService(ALARM_SERVICE);


        monthOfTheYear.put("يناير", 1);
        monthOfTheYear.put("فبراير", 2);
        monthOfTheYear.put("مارس", 3);
        monthOfTheYear.put("أبريل", 4);
        monthOfTheYear.put("مايو", 5);
        monthOfTheYear.put("يونيو", 6);
        monthOfTheYear.put("يوليو", 7);
        monthOfTheYear.put("أغسطس", 8);
        monthOfTheYear.put("سبتمبر", 9);
        monthOfTheYear.put("أكتوبر", 10);
        monthOfTheYear.put("نوفمبر", 11);
        monthOfTheYear.put("ديسمبر", 12);

        //Parse.enableLocalDatastore(this);
        Parse.initialize(this, "b7s5YTNUdSniQmzF19qOC8BJglirhlxuoCTOkIBc", "RBXE6w9AjSYPrIwZfr6sh7VPLTYt1yboUrSSYs2b");
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    public static void openPlayStor(String appPackageName) {
        try {
            APP_CTX.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(appPackageName)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (android.content.ActivityNotFoundException anfe) {
            APP_CTX.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    public static void changeTabsFont(TabLayout tabLayout) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(MyApplication.font);
                }
            }
        }
    }

    public static Long getCurretnDateTime() {
        Calendar rightNow = Calendar.getInstance();
        return (rightNow.getTimeInMillis());
    }

    public static Long getCurrentOffset() {
        Calendar rightNow = Calendar.getInstance();
        long offset = rightNow.get(Calendar.ZONE_OFFSET) +
                rightNow.get(Calendar.DST_OFFSET);

        return offset;
    }


}
