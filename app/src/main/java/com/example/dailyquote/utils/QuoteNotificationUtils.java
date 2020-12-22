package com.example.dailyquote.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dailyquote.R;
import com.example.dailyquote.model.Quote;

public class QuoteNotificationUtils {

    private static final String CHANNEL_ID = "quote_app";
    private static final int NOTIFICATION_ID = 99;

    public static void notifyUser(Context context, Quote quote) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(quote.getAuthor())
                .setContentText(quote.getQuote())
                .setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT);

        createNotificationChannel(context);

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null)
            manager.notify(NOTIFICATION_ID, builder.build());
        Log.d("TEST", "notifyUser: ");
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_desc);

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null)
                manager.createNotificationChannel(channel);
        }
    }
}
