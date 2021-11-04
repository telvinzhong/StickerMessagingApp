package edu.neu.madcourse.stickittothem;

import java.util.ArrayList;

public class User {
    String userName;
    ArrayList<String> stickersReceivedWho = new ArrayList<String>();
    ArrayList<String> stickersReceivedWhich = new ArrayList<String>();
    ArrayList<String> stickersReceivedWhen = new ArrayList<String>();
    int sticker1Sent = 0;
    int sticker2Sent = 0;
    int sticker3Sent = 0;


    public User(String userName){
        this.userName = userName;
    }
    public String getUserName() {
        return this.userName;
    }
}
