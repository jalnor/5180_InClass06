package com.example.gameon.inclass06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class MessageActivity extends AppCompatActivity implements GetRequestsAsync.GetJson {

    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        Threads thread = (Threads) getIntent().getSerializableExtra("Thread");
        Log.d("oh", "this is the thread received " + thread);
        String key = getIntent().getStringExtra("Key");
        ///String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/messages/" + thread.getId();
        String body = null;

        TextView title = findViewById(R.id.thread_title_tv);
        title.setText(thread.getTitle());


        //new GetRequestsAsync(MessageActivity.this).execute(url, body, key);




        findViewById(R.id.home_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(MessageActivity.this, MessageThreadsActivity.class);
            }
        });


        findViewById(R.id.send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public void passJson(JSONObject json) {
        this.json = json;

        Log.d("oh", "this is the json at passson " + json);
    }

    @Override
    public void getProgress(int progress) {

    }
}
