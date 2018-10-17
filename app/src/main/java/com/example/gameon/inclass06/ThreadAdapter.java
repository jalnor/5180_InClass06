package com.example.gameon.inclass06;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ThreadAdapter extends ArrayAdapter<ThreadTitle> {


    public ThreadAdapter(@NonNull Context context, int resource, @NonNull List<ThreadTitle> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ThreadTitle str = getItem(position);
        ViewHolder vh = null;

        if ( convertView != null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.thread_card, parent, false);
            vh = new ViewHolder();
            vh.thread = convertView.findViewById(R.id.threadTitle);
            vh.btn = convertView.findViewById(R.id.imageView);
        }
        try {
            vh.thread.setText(str.getTitle());
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView thread;
        ImageView btn;
    }
}
