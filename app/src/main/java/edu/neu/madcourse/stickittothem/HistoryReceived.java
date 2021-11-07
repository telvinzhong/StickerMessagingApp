package edu.neu.madcourse.stickittothem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.List;
import android.widget.ImageView;
import android.content.res.TypedArray;
import android.widget.Toast;

public class HistoryReceived extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String> s1, s2, s3;
    TypedArray images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_received);
        recyclerView = findViewById(R.id.recyclerView);

        images = getResources().obtainTypedArray(R.array.database_images);
        //images.recycle();

        if (User.getGlobalUser() == null) {
            Toast.makeText(getApplicationContext(), "Please log in first", Toast.LENGTH_SHORT).show();
            return;
        }
        s1 = User.getGlobalUser().getStickersReceivedWho();
        s2 = User.getGlobalUser().getStickersReceivedWhen();
        s3 = User.getGlobalUser().getStickersReceivedWhich();

        MyAdapter myAdapter = new MyAdapter(this, s1, s2, s3, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}