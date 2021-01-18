package base.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import id.collection.mobile.application.R;
import ops.screen.MainActivityDashboard;
import user.login.LoginActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private Map<String, String> bundle;
    private NotificationManager notificationManager;
    private String message;
    private String title;
    private String data1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);

        Log.e("firebase", "Notification Message Body: " +
                remoteMessage.getNotification().getBody());
        bundle = remoteMessage.getData();
//        Log.e("firebase", "Title: " + bundle.get("title"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(
                    getResources().getString(R.string.default_notification_channel_id),
                    getResources().getString(R.string.default_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription("Push Notification");
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setLightColor(Color.BLUE);
            mChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        if(remoteMessage == null)
        {
            Log.e("OKE ","NO MESSAGE");
            return;
        }
        if(bundle != null)
        {
            Log.e("OKE ","MESSAGE NOT NULL");
            if (bundle.get("message") != null)
                message = bundle.get("message");
            if (bundle.get("title") != null)
                title = bundle.get("title");
            if (bundle.get("data1") != null)
                data1 = bundle.get("data1");

            handleNotification(remoteMessage.getNotification().getBody(),title);
        }

    }

    public void handleNotification(String message, String title)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("sk", "reminder");
        intent.putExtra("ppknum", data1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getResources().getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.mipmap.ic_launcher).setColor(getResources().getColor(R.color.button))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message).setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
    }

}
