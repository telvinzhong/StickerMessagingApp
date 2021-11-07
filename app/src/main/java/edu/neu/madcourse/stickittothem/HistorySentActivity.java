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
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        user = User.getGlobalUser().getUserName();
//
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data: snapshot.getChildren()){
//                    // User exist
//                    if (data.child("sticker1Sent").exists()){
//                        one = (int) data.child("sticker1Sent").getValue();
//                    }
//                    // User not exist
//                    else{
//                        Toast.makeText(getApplicationContext(), "Error in fetching data.", Toast.LENGTH_SHORT).show();
//                    }
//                };
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });

//        int one = User.getGlobalUser().getSticker1Sent();
//        int two = User.getGlobalUser().getSticker2Sent();
//        int three = User.getGlobalUser().getSticker3Sent();

        TextView oneCount = findViewById(R.id.oneCount);
        TextView twoCount = findViewById(R.id.twoCount);
        TextView threeCount = findViewById(R.id.threeCount);

        oneCount.setText(one);
        twoCount.setText(two);
        threeCount.setText(three);

    }
}