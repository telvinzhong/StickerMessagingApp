package edu.neu.madcourse.stickittothem;

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


    public User(String userName){
        this.userName = userName;
        this.sticker1Sent = 0;
        this.sticker2Sent = 0;
        this.sticker3Sent = 0;
        this.stickersReceivedWhen = new ArrayList<>();
        this.stickersReceivedWhich = new ArrayList<>();
        this.stickersReceivedWho = new ArrayList<>();

    }
    public String getUserName() {
        return this.userName;
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
