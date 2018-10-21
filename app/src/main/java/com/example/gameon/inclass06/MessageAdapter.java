package com.example.gameon.inclass06;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Messages>{

        private List<Messages> messages;
        private int resource;
        private Context context;

        public MessageAdapter (@NonNull Context context, int resource, @NonNull List<Messages> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.messages = objects;

        }

    @Override
    public int getPosition(@Nullable Messages item) {
        return super.getPosition(item);
    }


    @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            ViewHolder vh = null;
            //Threads str = getItem(position);

            if ( convertView == null ) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.thread_card_chatroom, parent, false);
                vh = new com.example.gameon.inclass06.MessageAdapter.ViewHolder();
                vh.messages = messages.get(position);
                vh.message = convertView.findViewById(R.id.tv_message);
                vh.userFname = convertView.findViewById(R.id.tv_user_frname);
                vh.userFname = convertView.findViewById(R.id.tv_user_lname);
                vh.pretty = convertView.findViewById(R.id.tv_pretty);
                vh.removeMessage = convertView.findViewById(R.id.iv_trash);
                vh.removeMessage.setTag(vh.messages);
                convertView.setTag(vh);
            }else {
                vh = (com.example.gameon.inclass06.MessageAdapter.ViewHolder) convertView.getTag();
            }


            try {
                //need to set the message from message edittext
                vh.message.setText(vh.messages.getMessage());
                //String uname = vh.Messages.getUsername();
                vh.userFname.setText(vh.messages.getUser_fname());
                vh.userLname.setText(vh.messages.getUser_lname());
                //need to define pretty time
               //vh.pretty.setText(vh.Messages.getPretty());
            } catch (Exception e) {
                e.printStackTrace();
            }


            return convertView;
        }

        private static class ViewHolder {
            Messages messages;
            TextView message, userFname, userLname, pretty;
            ImageView removeMessage;
        }
    }
