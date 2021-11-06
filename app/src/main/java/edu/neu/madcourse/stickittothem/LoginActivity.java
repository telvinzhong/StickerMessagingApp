package edu.neu.madcourse.stickittothem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference databaseReference = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Button loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(view -> {
            TextView userTextView = findViewById(R.id.username);
            loginUser(userTextView.getText().toString());
        });
    }

    private void showToast(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    private void loginUser(String user) {
        if (user.isEmpty()) {
            showToast(user);
            return;
        }
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot userData = snapshot.child("users").child(user);
                if (!userData.exists()) {
                    showToast("User " + user + " does not exist. Please sign-up first");
                    return;
                }
                User loadedUser = new User(user);
                loadedUser.loadFrom(userData);
                User.setGlobalUser(loadedUser);
                showToast("User " + user + " successfully login. Now you can send stickers! 🚀");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Log.d("User", loadedUser.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showToast("Failed to login: " + error.getMessage());
            }
        });
    }
}