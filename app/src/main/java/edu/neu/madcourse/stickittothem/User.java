package edu.neu.madcourse.stickittothem;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class User {
    String userName;
    List<String> stickersReceivedWho;
    List<String> stickersReceivedWhich;
    List<String> stickersReceivedWhen;
    int sticker1Sent;
    int sticker2Sent;
    int sticker3Sent;

    // Singleton since the APP can only have one user at a time.
    private static User globalUser = null;

    public static synchronized void setGlobalUser(User user) {
        globalUser = user;
    }

    public static synchronized User getGlobalUser() {
        return globalUser;
    }

    public User(String userName){
        this.userName = userName;
        this.sticker1Sent = 0;
        this.sticker2Sent = 0;
        this.sticker3Sent = 0;
        this.stickersReceivedWhen = new ArrayList<>();
        this.stickersReceivedWhich = new ArrayList<>();
        this.stickersReceivedWho = new ArrayList<>();
    }

    public void loadFrom(DataSnapshot userData) {
        for (DataSnapshot when : userData.child("stickersReceivedWhen").getChildren()) {
            stickersReceivedWhen.add(when.getValue(String.class));
        }
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName){
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
}
