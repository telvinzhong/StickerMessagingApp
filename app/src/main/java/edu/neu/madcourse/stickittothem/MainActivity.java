package edu.neu.madcourse.stickittothem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.neu.madcourse.stickittothem.notification.CloudMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CloudMessaging.setServerKey(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.signup:
                startActivity(new Intent(this, SignupActivity.class));
                break;
            case R.id.historyReceived:
                startActivity(new Intent(this, HistoryReceived.class));
                break;
            case R.id.historySent:
                startActivity(new Intent(this, HistorySentActivity.class));
                break;
            case R.id.sendButton:
                startActivity(new Intent(this, SendActivity.class));
                break;
        }
    }
}