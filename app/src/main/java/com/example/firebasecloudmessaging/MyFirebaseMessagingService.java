package com.example.firebasecloudmessaging;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String CHANNEL_ID = "MyNewNotification";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        if(message.getData().size()>0){
            Map<String, String > datamap = message.getData();

            String title = datamap.get("title");
            String body = datamap.get("body");
            String image = datamap.get("image");
// https://youtu.be/3NFmRvWApu8?si=PIAB_87r6Qq95h60&t=1004

            createNotification(body);
            addNotification(title, body, image);
        }
    }

    private void addNotification(String title, String body, String image) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        builder.setColor(getResources().getColor(R.color.black));
        builder.setContentTitle(title);
        builder.setContentText(body);

        if (image != null) {
            if (image.length()>10 && image.startsWith("http")) {
//                builder.setLargeIcon(getBi)
            }

        }
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setAutoCancel(true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        PendingIntent pendingIntent;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE);

        }else {
            pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        int NOTIFICATION_ID = 1;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            int notificationPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);

            if(notificationPermission == PackageManager.PERMISSION_GRANTED){
                managerCompat.notify(NOTIFICATION_ID, builder.build());

            }
        }
        else {
            managerCompat.notify(NOTIFICATION_ID, builder.build());
        }

    }

    private void createNotification(String body) {
        CharSequence name = "Notification_NAME";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(body);

            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
