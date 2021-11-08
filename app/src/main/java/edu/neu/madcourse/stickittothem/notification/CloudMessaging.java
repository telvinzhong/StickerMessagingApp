package edu.neu.madcourse.stickittothem.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import edu.neu.madcourse.stickittothem.MainActivity;
import edu.neu.madcourse.stickittothem.R;

public class CloudMessaging extends FirebaseMessagingService {
    private static final String TAG = "CloudMessaging";
    private static String SERVER_KEY = "";

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        FirebaseInstallations.getInstance().delete();
        super.onTaskRemoved(rootIntent);
    }

    private static Properties getProperties(Context context) {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("firebase.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static void setServerKey(Context context) {
        SERVER_KEY = "key=" + getProperties(context).getProperty("SERVER_KEY");
    }

    public static void sendNewStickerNotificationTo(String sender, String receiver) {
        // Prepare data
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {
            jNotification.put("title", "New sticker!");
            jNotification.put("body", "🎉Hooray! " + receiver + ", you just got a sticker from " + sender);
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "OPEN_ACTIVITY_1");

            jPayload.put("to", "/topics/" + receiver);
            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            private final String serverToken = SERVER_KEY;
            private final JSONObject payload = jPayload;

            @Override
            public void run() {
                sendMessageViaHttpAPI(serverToken, payload);
            }
        }).start();
    }

    private static void sendMessageViaHttpAPI(String serverToken, JSONObject jsonObject) {
        try {
            Log.d(TAG, "Sending payload: " + jsonObject.toString());
            // Open the HTTP connection and send the payload
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", serverToken);
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();

            try {
                // Read FCM response.
                Log.d(TAG, "Got response: " + extractMessage(conn.getInputStream()));
            } catch (IOException e) {
                Log.e(TAG, "Got error: " + extractMessage(conn.getErrorStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractMessage(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Got new token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        Log.d(TAG, "Got message from " + from);
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification());
        }
    }

    private void showNotification(RemoteMessage.Notification remoteNotification) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification;
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.channel_id), getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("A channel to receive notification for stickers");
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(this, getString(R.string.channel_id));
        } else {
            builder = new NotificationCompat.Builder(this);
        }


        notification = builder.setContentTitle(remoteNotification.getTitle())
                .setContentText(remoteNotification.getBody())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(0, notification);
    }
}
