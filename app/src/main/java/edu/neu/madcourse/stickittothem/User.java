package edu.neu.madcourse.stickittothem;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static final String TAG = User.class.getName();
    // Singleton since the APP can only have one user at a time.
    private static User globalUser = null;
    String userName;
    List<String> stickersReceivedWho;
    List<String> stickersReceivedWhich;
    List<String> stickersReceivedWhen;
    int sticker1Sent;
    int sticker2Sent;
    int sticker3Sent;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public User(String userName) {
        this.userName = userName;
        this.sticker1Sent = 0;
        this.sticker2Sent = 0;
        this.sticker3Sent = 0;
        this.stickersReceivedWhen = new ArrayList<>();
        this.stickersReceivedWhich = new ArrayList<>();
        this.stickersReceivedWho = new ArrayList<>();
    }

    public static synchronized User getGlobalUser() {
        return globalUser;
    }

    public static synchronized void setGlobalUser(User user) {
        if (globalUser == user) {
            return;
        }
        if (globalUser != null) {
            globalUser.unsync();
            globalUser.unsubscribeToTopic();;
        }
        globalUser = user;
        globalUser.subscribeToTopic();
    }

    private void subscribeToTopic() {
        final String currentUsername = userName;
        FirebaseMessaging.getInstance().subscribeToTopic(userName).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Failed to subscribe to topic: " + currentUsername);
            } else {
                Log.d(TAG, "Successfully subscribe to topic: " + currentUsername);
            }
        });
    }

    private void unsubscribeToTopic() {
        final String currentUsername = userName;
        FirebaseMessaging.getInstance().unsubscribeFromTopic(userName).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Failed to unsubscribe to topic: " + currentUsername);
            }
        });
    }

    private static int unbox(Integer i) {
        if (i == null) return 0;
        return i;
    }

    private void loadFrom(DataSnapshot userData) {
        sticker1Sent = unbox(userData.child("sticker1Sent").getValue(Integer.class));
        sticker2Sent = unbox(userData.child("sticker2Sent").getValue(Integer.class));
        sticker3Sent = unbox(userData.child("sticker3Sent").getValue(Integer.class));
        stickersReceivedWhen.clear();
        stickersReceivedWhich.clear();
        stickersReceivedWho.clear();
        for (DataSnapshot when : userData.child("stickersReceivedWhen").getChildren()) {
            stickersReceivedWhen.add(when.getValue(String.class));
        }
        for (DataSnapshot when : userData.child("stickersReceivedWhich").getChildren()) {
            stickersReceivedWhich.add(when.getValue(String.class));
        }
        for (DataSnapshot when : userData.child("stickersReceivedWho").getChildren()) {
            stickersReceivedWho.add(when.getValue(String.class));
        }
        Log.d(TAG, "loaded data from Firebase: " + toString());
    }

    public void syncWith(DataSnapshot userData) {
        loadFrom(userData);
        databaseReference = userData.getRef();
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadFrom(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void unsync() {
        databaseReference.removeEventListener(valueEventListener);
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSticker1Sent() {
        return sticker1Sent;
    }

    public int getSticker2Sent() {
        return sticker2Sent;
    }

    public int getSticker3Sent() {
        return sticker3Sent;
    }

    public List<String> getStickersReceivedWhen() {
        return stickersReceivedWhen;
    }

    public List<String> getStickersReceivedWhich() {
        return stickersReceivedWhich;
    }

    public List<String> getStickersReceivedWho() {
        return stickersReceivedWho;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", stickersReceivedWho=" + stickersReceivedWho +
                ", stickersReceivedWhich=" + stickersReceivedWhich +
                ", stickersReceivedWhen=" + stickersReceivedWhen +
                ", sticker1Sent=" + sticker1Sent +
                ", sticker2Sent=" + sticker2Sent +
                ", sticker3Sent=" + sticker3Sent +
                '}';
    }
}
