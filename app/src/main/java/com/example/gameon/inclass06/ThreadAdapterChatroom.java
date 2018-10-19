package com.example.gameon.inclass06;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ThreadAdapterChatroom extends ArrayAdapter<Threads>{

        private List<Threads> threads;
        private int resource;
        private Context context;

        public ThreadAdapterChatroom(@NonNull Context context, int resource, @NonNull List<Threads> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.threads = objects;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            com.example.gameon.inclass06.ThreadAdapterChatroom.ViewHolder vh = null;
            //Threads str = getItem(position);

            if ( convertView == null ) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.thread_card_chatroom, parent, false);
                vh = new com.example.gameon.inclass06.ThreadAdapterChatroom.ViewHolder();
                vh.Threads = threads.get(position);
                vh.message = convertView.findViewById(R.id.tv_message);
                vh.username = convertView.findViewById(R.id.tv_username);
                vh.pretty = convertView.findViewById(R.id.tv_pretty);
                //vh.removeThread = convertView.findViewById(R.id.imageView);
                //vh.removeThread.setTag(vh.Threads);
                convertView.setTag(vh);
            }else {
                vh = (com.example.gameon.inclass06.ThreadAdapterChatroom.ViewHolder) convertView.getTag();
            }


            try {
                //need to set the message from message edittext
                vh.message.setText(vh.Threads.getMessage());
                String uname = vh.Threads.getUser_fname() + vh.Threads.getUser_lname();
                vh.username.setText(uname);
                //need to define pretty
                vh.pretty.setText(vh.Threads.getPretty());
            } catch (Exception e) {
                e.printStackTrace();
            }


            return convertView;
        }

        private static class ViewHolder {
            Threads Threads;
            TextView message, username, pretty;
           // ImageView removeThread;
        }
    }
