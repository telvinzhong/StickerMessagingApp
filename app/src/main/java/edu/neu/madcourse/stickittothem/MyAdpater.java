package edu.neu.madcourse.stickittothem;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// create new MyAdapter class
class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<String> data1, data2, data3;
    TypedArray images;
    Context context;
    public MyAdapter(Context ct, List<String> s1, List<String> s2, List<String> s3, TypedArray img){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // store the inflater into view
        View view = inflater.inflate(R.layout.row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text1.setText("Sent by: " + data1.get(position));
        holder.text2.setText("Sent time: " + data2.get(position));
        holder.text3.setText("Sticker number: " + data3.get(position));
        //holder.image.setImageResource(Integer.valueOf(images.get(position)));
        if (Integer.valueOf(data3.get(position)) == 1){
            holder.image.setImageResource(Integer.valueOf(images.getResourceId(0, -1)));
        } else if (Integer.valueOf(data3.get(position)) == 2){
            holder.image.setImageResource(Integer.valueOf(images.getResourceId(1, -1)));
        } else if (Integer.valueOf(data3.get(position)) == 3){
            holder.image.setImageResource(Integer.valueOf(images.getResourceId(2, -1)));
        }
    }

    @Override
    public int getItemCount() {
        return data3.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text1, text2, text3;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.user);
            text2 = itemView.findViewById(R.id.time);
            text3 = itemView.findViewById(R.id.sticker);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}