/*
Assignment HW#6
Page MessageThreadActivity.java
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
import android.widget.TextView;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MessageThreadsActivity extends AppCompatActivity implements GetRequestsAsync.GetJson, DeleteThreadInterface {

    ArrayList<Threads> allThreads = new ArrayList<>();
    ArrayList<Threads> userThreads = new ArrayList<>();
    String n, postBody, body2;
    Threads currentThread;
    Gson gson = new Gson();
    JSONObject json = null;
    int progress = 0;
    String userID;
    private RecyclerView threadRecyclerView;
    private RecyclerView.Adapter threadAdapter;
    private RecyclerView.LayoutManager threadLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);


        final String key = getIntent().getStringExtra("Key");
        final User user = (User) getIntent().getSerializableExtra("User");
        final String firstName = user.getUser_fname();
        final String lastName = user.getUser_lname();

        SharedPreferences sharedPref = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        final String userFName = sharedPref.getString("userFName", null);
        final String userLName = sharedPref.getString("userLName", null);
        userID = sharedPref.getString("userID", null);

        threadRecyclerView = findViewById(R.id.threadRecyclerView);
        threadRecyclerView.setHasFixedSize(true);

        threadLayoutManager = new LinearLayoutManager(this);
        threadRecyclerView.setLayoutManager(threadLayoutManager);

        threadAdapter = new ThreadThreadAdapter(allThreads, userID, this);
        threadRecyclerView.setAdapter(threadAdapter);



        TextView userName = findViewById(R.id.userName);
        String username = firstName + " " + lastName;
        userName.setText(username);
        n = "BEARER " + key;
        body2 = null;
        getAllThreads();

        findViewById(R.id.addnewthreadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newThreads = findViewById(R.id.newThread);
                postBody = newThreads.getText().toString();
                body2 = null;
                String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/add";
                new GetRequestsAsync(MessageThreadsActivity.this).execute(url, postBody, n, body2);
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
//        Log.d("oh", "This is json comning from asynctask" + json + " the allFlag is " + getAllFlag + " addFlag " + addNewFlag);
        this.json = json;
        try {
//            Log.d("A", "This is allThreads in passJson before load " + allThreads);
            if ( this.json.has("threads") ) {
                if ( allThreads != null ) {
                    this.allThreads.clear();
                }
                JSONArray temp = this.json.getJSONArray("threads");
                for (int i = 0; i < temp.length(); i++) {
//                    if ( temp.getJSONObject(i).getString("user_id").equals(userID) ) {
                        this.allThreads.add(this.gson.fromJson(temp.get(i).toString(), Threads.class));
//                    }
                }
                setListView();
//                getAllFlag = false;
            } else if ( this.json.has("thread") ) {
                getAllThreads();
            }
            Log.d("A", "This is allThreads in passJson after load " + allThreads);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getAllThreads() {
        String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread";
        String body = null;
        new GetRequestsAsync(MessageThreadsActivity.this).execute(url, body, n, body2);
    }

    @Override
    public void getProgress(int progress) {
        this.progress = progress;
    }

    public void setListView()  {
        threadAdapter.notifyDataSetChanged();
    }



    @Override
    public void deleteThread(String id) {
        String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/delete/" + id;
        String body = null;
        new GetRequestsAsync(MessageThreadsActivity.this).execute(url, body, n, body2);
        getAllThreads();
    }

    @Override
    public void currentThread(Threads currentThread) {
        Log.d("ohMan", "THis is in MessageThread " + currentThread);
        Intent intent = new Intent(MessageThreadsActivity.this, MessageActivity.class);
        intent.putExtra("Thread", currentThread);
        startActivity(intent);
    }


}









