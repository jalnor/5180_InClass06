package com.example.gameon.inclass06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MessageActivity extends AppCompatActivity implements GetRequestsAsync.GetJson {

    JSONObject json;
    Gson gson = new Gson();
    String url, key, body, body2, flag;
    Threads thread;
    ListView listOfMessages;
    ArrayList<Messages> messages = new ArrayList<>();
    MessageAdapter adapter;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("Chatroom");

        listOfMessages = findViewById(R.id.message_lv);
        adapter = new MessageAdapter(MessageActivity.this, R.layout.thread_card_chatroom, messages);
        listOfMessages.setAdapter(adapter);


        thread = (Threads) getIntent().getSerializableExtra("Thread");
        Log.d("oh", "this is the thread received " + thread);
        key = getIntent().getStringExtra("Key");
        url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/messages/" + thread.getId();
        body = null;
        body2 = null;

        title = findViewById(R.id.thread_title_tv);
        title.setText(thread.getTitle());

        new GetRequestsAsync(MessageActivity.this).execute(url, body, key, body2);
        flag = "getAllMessages";



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

                new GetRequestsAsync(MessageActivity.this).execute(url, body, key, body2);
                message.setText("");
                flag = "sendMessage";
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listMessages();
            }
        });


    }

    @Override
    public void passJson(JSONObject json) {
        this.json = json;
        Log.d("oh", "This is the value of the flag: " + flag);
        try {
            if (flag.equals("getAllMessages")) {
                messages.clear();
                JSONArray messageArray = this.json.getJSONArray("messages");
                for (int i = 0; i < messageArray.length(); i++) {
                    messages.add(gson.fromJson(messageArray.get(i).toString(), Messages.class));
                }
            } else if (flag.equals("sendMessage")) {
                JSONObject newMessage = this.json.getJSONObject("message");
                messages.add(gson.fromJson(newMessage.toString(), Messages.class));
            }

            Log.d("oh", "this is the json at passjson in message " + json);
            flag = "";
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getProgress(int progress) {

    }

    public void listMessages() {

        adapter.addAll(messages);
    }

    public void removeMessage(View view) {
        flag = "removeFlag";
        Messages message = (Messages) view.getTag();
        adapter.remove(message);
        String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/message/delete/" + message.getId();
        String body = null;
        new GetRequestsAsync(MessageActivity.this).execute(url,body,key);
    }
}
