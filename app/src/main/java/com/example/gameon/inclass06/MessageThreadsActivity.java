package com.example.gameon.inclass06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MessageThreadsActivity extends AppCompatActivity implements GetRequestsAsync.GetJson {

    ArrayList<Threads> allThreads = new ArrayList<>();
    ArrayList<Threads> userThreads = new ArrayList<>();
    String n;
    Gson gson = new Gson();
    JSONObject json = null;
    String postBody;
    int progress = 0;
    ThreadAdapter adapter;
    boolean getAllFlag = false;
    boolean addNewflag = false;
    String flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);


        final String key = getIntent().getStringExtra("Key");
        final String firstName = getIntent().getStringExtra("FirstName");
        final String lastName = getIntent().getStringExtra("LastName");

        ListView listView = findViewById(R.id.lv);
        adapter = new ThreadAdapter(MessageThreadsActivity.this, R.layout.thread_card, userThreads);
        listView.setAdapter(adapter);


        TextView user = findViewById(R.id.userName);
        String username = firstName + " " + lastName;
        user.setText(username);
        n = "BEARER " + key;

        String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread";
        String body = null;
        new GetRequestsAsync(MessageThreadsActivity.this).execute(url, body, n);
        flag = "getAllFlag";

        findViewById(R.id.addnewthreadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newThreads = findViewById(R.id.newThread);
                postBody = newThreads.getText().toString();
                String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/add";
                new GetRequestsAsync(MessageThreadsActivity.this).execute(url, postBody, n);
                flag = "addNewflag";
                newThreads.setText("");
            }
        });

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().removeExtra("Key");
                Intent intent = new Intent(MessageThreadsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void passJson(JSONObject json) {
        Log.d("oh", "This is json comning from asynctask" + json + " the allFlag is " + getAllFlag + " addFlag " + addNewflag);
        this.json = json;
        if ( getAllFlag ) {
            try {
                JSONObject temp = this.json.getJSONObject("threads");

                this.allThreads.add(this.gson.fromJson(temp.toString(), Threads.class));
                Log.d("oh", "This is the first in allthreads  " + this.allThreads.get(0).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if ( addNewflag ) {
            try {
                setListView();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getProgress(int progress) {
        this.progress = progress;
        if (progress == 100) {
            if (flag.equals("getAllFlag")) {
                getAllFlag = true;
            } else {
                addNewflag = true;
            }
        }
    }



    public void setListView() throws JSONException {

        JSONObject temp = this.json.getJSONObject("thread");
        adapter.add(this.gson.fromJson(temp.toString(), Threads.class));
        //userThreads.add(this.gson.fromJson(temp.toString(), Threads.class));
        Log.d("oh", "This is userTHreads in setListView " + userThreads.toString());
    }

    public void removeThread(View view) {
        Threads thread = (Threads) view.getTag();

        System.out.println("Value in removeThread " + thread);
        adapter.remove(thread);
        String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/delete/" + thread.getId();
        String body = null;
        new GetRequestsAsync(MessageThreadsActivity.this).execute(url,body,n);
    }
}









