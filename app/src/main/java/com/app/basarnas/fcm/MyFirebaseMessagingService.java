/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.basarnas.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.app.basarnas.R;
import com.app.basarnas.models.DataNotification;
import com.app.basarnas.modules.main.MainActivity;
import com.app.basarnas.utility.CommonUtilities;
import com.app.basarnas.utility.Config;
import com.app.basarnas.utility.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    DataNotification dataNotification;
    String jsonDataNotification;
    Preferences pref;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            pref = new Preferences(getApplicationContext());

            Map<String, String> dataPayload = remoteMessage.getData();
            jsonDataNotification = new Gson().toJson(dataPayload);
            dataNotification = new Gson().fromJson(jsonDataNotification, new TypeToken<DataNotification>() {
            }.getType());

            switch (dataNotification.getType().toLowerCase()) {
                case "actionbar":
                    sendNotification();
                    break;
                case "popup":
                    launch_activity(PopUpNotif.class);
                    break;
                case "ads":
                    launch_activity(PopUpNotifFull.class);
                    break;
                case "hidden":
                    /* Without Action */
                    break;
                default:
                    sendNotification();
                    launch_activity(PopUpNotif.class);
                    break;
            }

            // Action to save preference {dataNotification.getKeyValue(), dataNotification.getTypeValue(), dataNotification.getValue()}
            if (dataNotification.getKeyValue() != null && !dataNotification.getKeyValue().isEmpty()) {
                if (dataNotification.getTypeValue().equalsIgnoreCase("integer")) {
                    int value = Integer.parseInt(dataNotification.getValue());
                    pref.savePreferences(dataNotification.getKeyValue(), value);
                } else {
                    pref.savePreferences(dataNotification.getKeyValue(), dataNotification.getValue());
                }
            }

            // Action to update data EventBus
            switch (dataNotification.getMethod().toLowerCase()) {
                case "deposit":
                    EventBus.getDefault().post(dataNotification);
                    break;
                default:
                    break;
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    // [END receive_message]

    private void launch_activity(Class cls) {
        Intent i = new Intent();
        i.putExtra(Config.JSON_DATA_NOTIFICATION, jsonDataNotification);
        i.setAction(Intent.ACTION_MAIN);
        i.setClass(this, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Config.JSON_DATA_NOTIFICATION, jsonDataNotification);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(dataNotification.getText()))
                .setSmallIcon(R.mipmap.ic_launcher, 10)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .setContentTitle(dataNotification.getTitle())
                .setContentText(CommonUtilities.toHtml(dataNotification.getText()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Config.NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());
    }
}

