/*
Assignment HW#6
Page MessageActivity.java
Authors Jarrod Norris, Abinandaraj Rajendran, Carrie Mao
 */
package com.example.gameon.inclass06;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
//import org.ocpsoft.prettytime.PrettyTime;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity implements GetRequestsAsync.GetJson, DeleteMessageInterface {

    JSONObject json;
    Gson gson = new Gson();
    String url, key, body, body2, flag, userID, token;

    ArrayList<Messages> messages = new ArrayList<>();
    TextView title;
    Threads thread;
    private RecyclerView messageRecyclerView;
    private RecyclerView.Adapter messageAdapter;
    private RecyclerView.LayoutManager messageLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("Chatroom");

        SharedPreferences sharedPref = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        userID = sharedPref.getString("userID", null);
        key = sharedPref.getString("token", null);
        token = "Bearer " + key;
        Log.d("ohMan", "This is the key from shared preferences " + key);


        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        messageRecyclerView.setHasFixedSize(true);

        messageLayoutManager = new LinearLayoutManager(this);
        messageRecyclerView.setLayoutManager(messageLayoutManager);

        messageAdapter = new MessageAdapter(messages, userID, this);
        messageRecyclerView.setAdapter(messageAdapter);


        thread =  (Threads) getIntent().getSerializableExtra("Thread");
//        Log.d("oh", "this is the thread received " + thread);
        //key = getIntent().getStringExtra("Key");

        title = findViewById(R.id.thread_title_tv);
        title.setText(thread.getTitle());
        body2 = null;
        getAllMessages();

        findViewById(R.id.home_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messages.clear();
                title.setText("");
                Intent home = new Intent(MessageActivity.this, MessageThreadsActivity.class);
                finish();
            }
        });


        findViewById(R.id.send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText message = findViewById(R.id.message_body);

                url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/message/add?";
                body = thread.getId();
                body2 = message.getText().toString();

                new GetRequestsAsync(MessageActivity.this).execute(url, body, token, body2);
                message.setText("");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public void passJson(JSONObject json) {
        this.json = json;

        try {
            if ( this.json.has("messages")) {
                if( this.messages != null ) {
                    this.messages.clear();
                }
                JSONArray messageArray = this.json.getJSONArray("messages");
                for (int i = 0; i < messageArray.length(); i++) {
                    this.messages.add(gson.fromJson(messageArray.get(i).toString(), Messages.class));
                }
                Log.d("oh", "This length of messageArray is  " + messageArray.length());
                setMessageView();
            } else if ( this.json.has("message") ) {
                getAllMessages();
            }

            Log.d("oh", "this is the json at passjson in message " + json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getProgress(int progress) {

    }

    public void getAllMessages() {
        url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/messages/" + thread.getId();
        body = null;
        body2 = null;

        new GetRequestsAsync(MessageActivity.this).execute(url, body, token, body2);
    }

    public void setMessageView() {
        messageAdapter.notifyDataSetChanged();

    }


    @Override
    public void deleteMessage(String id) {
        String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/message/delete/" + id;
        String body = null;
        body2 = null;
        new GetRequestsAsync(MessageActivity.this).execute(url, body, token, body2);
        getAllMessages();
    }
}
