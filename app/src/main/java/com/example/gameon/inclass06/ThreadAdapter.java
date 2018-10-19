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

public class ThreadAdapter extends ArrayAdapter<Threads> {


    private List<Threads> threads;
    private int resource;
    private Context context;

    public ThreadAdapter(@NonNull Context context, int resource, @NonNull List<Threads> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.threads = objects;
    }

    @Override
    public int getPosition(@Nullable Threads item) {
        return super.getPosition(item);
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder vh = null;
        //Threads str = getItem(position);

        if ( convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.thread_card, parent, false);
            vh = new ViewHolder();
            vh.threads = threads.get(position);
            vh.thread = convertView.findViewById(R.id.threadTitle);
            vh.removeThread = convertView.findViewById(R.id.imageView);
            vh.removeThread.setTag(vh.threads);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        try {
            vh.thread.setText(vh.threads.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
    private static class ViewHolder {
        Threads threads;
        TextView thread;
        ImageView removeThread;

    }
}
