package com.example.newsten;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;

public class NotifcationReciver extends BroadcastReceiver {

    final int NOTIF_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {




        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId=null;

        if(Build.VERSION.SDK_INT>=26){

            channelId="news_channel";
            CharSequence channelName = "News channel";


            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,importance);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(),channelId).setSmallIcon(R.drawable.news);



        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.notifcation_layout);
        RemoteViews bigRemoteViews = new RemoteViews(context.getPackageName(),R.layout.notification_big_layout);



        Intent notifIntent = new Intent(context,Detailed.class);
        PendingIntent notifPedingIntent = PendingIntent.getActivity(context,0,notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notif_id_small,notifPedingIntent);
        bigRemoteViews.setOnClickPendingIntent(R.id.notif_big_id,notifPedingIntent);
        builder.setCustomContentView(remoteViews).setCustomBigContentView(bigRemoteViews);

        //load the article data into the notification
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        remoteViews.setTextViewText(R.id.article_notif_title,sp.getString("title","title"));
        remoteViews.setImageViewResource(R.id.icon_iv,R.drawable.news);
        final Notification notification = builder.build();

        NotificationTarget notificationTarget = new NotificationTarget(context,R.id.article_notif_image, remoteViews,notification , NOTIF_ID);

        Glide.with(context.getApplicationContext()).asBitmap().load(sp.getString("imageUrl","")).into(notificationTarget);

        NotificationTarget notificationTargetBig = new NotificationTarget(context,R.id.notif_big_iv, bigRemoteViews,notification , NOTIF_ID);

        Glide.with(context.getApplicationContext()).asBitmap().load(sp.getString("imageUrl","")).into(notificationTargetBig);

        bigRemoteViews.setTextViewText(R.id.title_big_content,sp.getString("title","title"));
        bigRemoteViews.setTextViewText(R.id.body_big_content,sp.getString("content","content"));
        bigRemoteViews.setImageViewResource(R.id.icon_big_iv,R.drawable.news);




        notificationManager.notify(NOTIF_ID,builder.build());



        Toast.makeText(context, "Notif accept", Toast.LENGTH_SHORT).show();

        int time = intent.getIntExtra("time",60);
        Log.d("ALMOGIERC",""+time);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + time*1000,pendingIntent);


    }



}
