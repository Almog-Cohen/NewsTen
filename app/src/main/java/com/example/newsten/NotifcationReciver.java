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
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifcationReciver extends BroadcastReceiver {

    final int NOTIF_ID = 1;
    final String API_KEY = "051f2230d01c4235abeff3f4aef74452";
    List<Articles> articles = new ArrayList<>();

    @Override
    public void onReceive(final Context context, Intent intent) {

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

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String category = intent.getStringExtra("category_notif");
        String country = intent.getStringExtra("country");
        Call<HeadLines> call = ApiClient.getInstance().getApi().getHeadLines(country,category,API_KEY);
        call.enqueue(new Callback<HeadLines>() {
            @Override
            public void onResponse(Call<HeadLines> call, Response<HeadLines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    articles.clear();
                    articles = response.body().getArticles();
                    saveNotificationArtilce(context);
                }
            }
            @Override
            public void onFailure(Call<HeadLines> call, Throwable t) {
                t.printStackTrace();
            }
        });

        //load the article data into the notification
        remoteViews.setTextViewText(R.id.article_notif_title,sp.getString("title","title"));
        remoteViews.setImageViewResource(R.id.icon_iv,R.drawable.news);
        final Notification notification = builder.build();
        NotificationTarget notificationTarget = new NotificationTarget(context,R.id.article_notif_image, remoteViews,notification , NOTIF_ID);
        Glide.with(context.getApplicationContext()).asBitmap().load(sp.getString("imageUrl","imageUrl")).into(notificationTarget);

        NotificationTarget notificationTargetBig = new NotificationTarget(context,R.id.notif_big_iv, bigRemoteViews,notification , NOTIF_ID);
        Glide.with(context.getApplicationContext()).asBitmap().load(sp.getString("imageUrl","imageUrl")).into(notificationTargetBig);
        bigRemoteViews.setTextViewText(R.id.title_big_content,sp.getString("title","title"));
        bigRemoteViews.setTextViewText(R.id.body_big_content,sp.getString("content","content"));
        bigRemoteViews.setImageViewResource(R.id.icon_big_iv,R.drawable.news);

        notificationManager.notify(NOTIF_ID,builder.build());

        int time = intent.getIntExtra("time",60);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + time*1000,pendingIntent);
    }

    // Saving one random  article for the notification
    public void saveNotificationArtilce(Context context){

        Random rand = new Random();
        int randomArticle=rand.nextInt(articles.size());

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("title",articles.get(randomArticle).getTitle());
        editor.putString("content",articles.get(randomArticle).getContent());
        editor.putString("url",articles.get(randomArticle).getUrlAdress());
        editor.putString("imageUrl",articles.get(randomArticle).getUrlToImage());
        editor.apply();

    }

}
