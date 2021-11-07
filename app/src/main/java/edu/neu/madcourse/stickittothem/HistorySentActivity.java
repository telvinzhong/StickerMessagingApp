package edu.neu.madcourse.stickittothem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.*;

public class HistorySentActivity extends AppCompatActivity {
    private String user;
    private DatabaseReference mDatabase;
    private int one = 5;
    private int two = 5;
    private int three = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_sent);

        if (User.getGlobalUser() == null) {
            Toast.makeText(getApplicationContext(), "Please log in first", Toast.LENGTH_SHORT).show();
            return;
        }

        one = User.getGlobalUser().getSticker1Sent();
        two = User.getGlobalUser().getSticker2Sent();
        three = User.getGlobalUser().getSticker3Sent();

        TextView oneCount = findViewById(R.id.oneCount);
        TextView twoCount = findViewById(R.id.twoCount);
        TextView threeCount = findViewById(R.id.threeCount);

        oneCount.setText(String.valueOf(one));
        twoCount.setText(String.valueOf(two));
        threeCount.setText(String.valueOf(three));

    }
}