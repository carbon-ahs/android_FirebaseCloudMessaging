package com.example.firebasecloudmessaging;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        if(message.getData().size()>0){
            Map<String, String > datamap = message.getData();

            String title = datamap.get("title");
            String body = datamap.get("body");
            String image = datamap.get("image");
// https://youtu.be/3NFmRvWApu8?si=PIAB_87r6Qq95h60&t=1004
        }
    }


}
