package edu.neu.madcourse.stickittothem;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class SendActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private RadioButton cb1;
    private RadioButton cb2;
    private RadioButton cb3;
    private static final String TAG = "stickit";

    TextView re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        // Connect to db here
        mDatabase = FirebaseDatabase.getInstance().getReference();

        cb1 = findViewById(R.id.checkBox1);
        cb2 = findViewById(R.id.checkBox2);
        cb3 = findViewById(R.id.checkBox3);
        Button registerButton = findViewById(R.id.send);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get username input
                re = findViewById(R.id.receiver);
                String receiver = re.getText().toString();
                // check if input is empty or box is not chosen
                if (receiver.isEmpty() || !cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked()){
                    Toast.makeText(getApplicationContext(), "Please enter username or pick a sticker", Toast.LENGTH_SHORT).show();
                }
                else{
                    // check for user exist before sending
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data: snapshot.getChildren()){
                                // User exist
                                if (data.child(receiver).exists()){
                                    sendStickers(receiver);
                                }
                                // User not exist
                                else{
                                    Toast.makeText(getApplicationContext(), "User not exist", Toast.LENGTH_SHORT).show();
                                }
                            };
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }

    // Need to update current user
    public void sendStickers(String receiver){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        if (cb1.isChecked()){
            mDatabase.child("users").child(receiver).child("stickersReceivedWhen").push().setValue(formatter.format(date));
            mDatabase.child("users").child(receiver).child("stickersReceivedWho").push().setValue("Current user");
            mDatabase.child("users").child(receiver).child("stickersReceivedWhich").push().setValue("1");
            Toast.makeText(getApplicationContext(), "Sticker " + "1 " + "sent successfully to " + receiver, Toast.LENGTH_SHORT).show();

        }
        else if (cb2.isChecked()){
            mDatabase.child("users").child(receiver).child("stickersReceivedWhen").push().setValue(formatter.format(date));
            mDatabase.child("users").child(receiver).child("stickersReceivedWho").push().setValue("Current user");
            mDatabase.child("users").child(receiver).child("stickersReceivedWhich").push().setValue("2");
            Toast.makeText(getApplicationContext(), "Sticker " + "1 " + "sent successfully to " + receiver, Toast.LENGTH_SHORT).show();

        }
        else{
            mDatabase.child("users").child(receiver).child("stickersReceivedWhen").push().setValue(formatter.format(date));
            mDatabase.child("users").child(receiver).child("stickersReceivedWho").push().setValue("Current user");
            mDatabase.child("users").child(receiver).child("stickersReceivedWhich").push().setValue("3");
            Toast.makeText(getApplicationContext(), "Sticker " + "1 " + "sent successfully to " + receiver, Toast.LENGTH_SHORT).show();
        }
        re.setText("");
    }
    // thing need to do
//    check for login before sending
//    update stickersent after sending
// update currentuser when sending
}
