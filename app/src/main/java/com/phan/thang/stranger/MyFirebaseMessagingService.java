package com.phan.thang.stranger;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public SharedPreferences prefs;
    private User user;
    private FDatabase data;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        prefs = this.getSharedPreferences("com.phan.thang.stranger", Context.MODE_PRIVATE);
        this.user = getUser();
        data = new FDatabase();

        System.out.println(user.toString());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData().toString(), user.notification, user.sound, getVibrateLong());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            sendNotification(remoteMessage.getNotification().getBody(), user.notification, user.sound, getVibrateLong());
        }
    }

    private void sendNotification(String messageBody, String notificationEnabled, String soundEnabled, long[] vibrateEnabled) {
        if(notificationEnabled.equals("On")){
            Intent intent = new Intent(this, MainActivity.class);
            String theBody = messageBody.replace("{data=", "").replace("}", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri;

            if (soundEnabled.equals("On")) {
                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }else{
                defaultSoundUri = null;
            }

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_plus_one_black_24dp)
                    .setContentTitle("Stranger!")
                    .setContentText(theBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setVibrate(vibrateEnabled)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }

        this.user.notificationCount = user.notificationCount + 1;
        data.addNotificationCount(user.notificationCount, user.UID);
        saveSettings();
    }

    private long[] getVibrateLong(){
        if(user.vibrate.equals("On")){
            return new long[]{200,200,200,200};
        }else{
            return null;
        }
    }

    private User getUser(){
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User u = gson.fromJson(json, User.class);
        return u;
    }

    private void saveSettings(){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefs.edit().putString("user", json).apply();
    }
}