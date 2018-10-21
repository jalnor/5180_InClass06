/*
Assignment HW#6
Page ThreadTHreadAdapter.java
Authors Jarrod Norris, Abinandaraj Rajendran, Carrie Mao
 */
package com.example.gameon.inclass06;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class ThreadThreadAdapter extends RecyclerView.Adapter<ThreadThreadAdapter.ViewHolder> {


    ArrayList<Threads> threads;
    String userID;
    DeleteThreadInterface dt;

    public ThreadThreadAdapter(ArrayList<Threads> threads, String userID, DeleteThreadInterface dt) {
        this.threads = threads;
        this.userID = userID;
        this.dt = dt;
    }

    @NonNull
    @Override
    public ThreadThreadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View threadCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.thread_card, parent, false);
        ViewHolder vh = new ViewHolder(threadCard);
        vh.dt = this.dt;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Threads thread = threads.get(position);
        holder.threadTitle.setText(thread.title);
        holder.threadId = thread.id;
        holder.currentThread = thread;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ohMan", "This is inside holder.onCLick in adapter " + thread);
                dt.currentThread(thread);
            }
        });

        if (thread.user_id.equals(this.userID) ) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
    }



    @Override
    public int getItemCount() {
        return threads.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder   {

        public TextView threadTitle;
        public ImageView imageView;
        public String threadId;
        public DeleteThreadInterface dt;
        public Threads currentThread;

        public ViewHolder(View itemView) {
            super(itemView);
            this.threadTitle = itemView.findViewById(R.id.threadTitle);
            this.imageView = itemView.findViewById(R.id.imageView);

            this.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dt.deleteThread(threadId);
                }
            });
        }



    }
}
