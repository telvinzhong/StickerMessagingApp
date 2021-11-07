package edu.neu.madcourse.stickittothem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HistorySentActivity extends AppCompatActivity {
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_sent);

        int one = User.getGlobalUser().getSticker1Sent();
        int two = User.getGlobalUser().getSticker2Sent();
        int three = User.getGlobalUser().getSticker3Sent();

        TextView oneCount = findViewById(R.id.oneCount);
        TextView twoCount = findViewById(R.id.twoCount);
        TextView threeCount = findViewById(R.id.threeCount);

        oneCount.setText(one);
        twoCount.setText(two);
        threeCount.setText(three);

    }
}