package com.smartgateapps.italyfootball.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.smartgateapps.italyfootball.italy.MyApplication;
import com.smartgateapps.italyfootball.model.Match;

import java.util.List;

/**
 * Created by Raafat on 13/02/2016.
 */
public class BootCompletReciever extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        //register update matches for the next matches
        List<Match> matches = Match.getAllUnUpdatedMatches();
        for(Match m : matches) {
            if(m.getNotifyDateTime() <= MyApplication.getCurretnDateTime()){
                m.setNotifyDateTime(MyApplication.getCurretnDateTime()+6*60*1000);
                m.update();
            }
            m.registerMatchUpdateFirstTime();
        }

        Intent intentActivationUpateNewsService = new Intent(MyApplication.ACTION_ACTIVATION);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(MyApplication.APP_CTX, 77, intentActivationUpateNewsService, PendingIntent.FLAG_UPDATE_CURRENT);

        MyApplication.alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, MyApplication.getCurretnDateTime() + 10000,10*60*1000, pendingIntent);
        //register do at 2 AM;


    }
}
