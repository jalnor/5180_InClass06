package com.example.gameon.inclass06;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    ArrayList<Messages> messages;
    String userId;
    DeleteMessageInterface dm;

    public MessageAdapter(ArrayList<Messages> messages, String userId, DeleteMessageInterface dm) {
        this.messages = messages;
        this.userId = userId;
        this.dm = dm;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View messageCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.thread_card_chatroom, parent, false);
        MessageViewHolder mvh = new MessageViewHolder(messageCard);
        mvh.dm = this.dm;
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Messages msg = messages.get(position);
        holder.message.setText(msg.message);
        holder.userFName.setText(msg.user_fname);
        holder.userLName.setText(msg.user_lname);
        holder.pretty.setText(msg.created_at);
        holder.pretty.setText(convertpretty(msg.created_at));
        holder.messageId = msg.id;

        if (msg.user_id.equals(this.userId) ) {
            holder.trash.setVisibility(View.VISIBLE);
        } else {
            holder.trash.setVisibility(View.INVISIBLE);
        }

    }

    String convertpretty (String dateString)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date convertedDate = new Date();

        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        PrettyTime p = new PrettyTime();

        String datetime = p.format(convertedDate);

        return datetime;
    }



    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public TextView userFName;
        public TextView userLName;
        public TextView pretty;
        public ImageView trash;
        public String messageId;
        public DeleteMessageInterface dm;



        public MessageViewHolder(View itemView) {
            super(itemView);
            this.message = itemView.findViewById(R.id.tv_message);
            this.userFName = itemView.findViewById(R.id.tv_user_frname);
            this.userLName = itemView.findViewById(R.id.tv_user_lname);
            this.pretty = itemView.findViewById(R.id.tv_pretty);
            this.trash = itemView.findViewById(R.id.iv_trash);

            this.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dm.deleteMessage(messageId);
                }
            });

        }
    }
}
