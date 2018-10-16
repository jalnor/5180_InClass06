package com.example.gameon.inclass06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MessageThreadsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);

            String key = getIntent().getStringExtra("Key");
            Log.d("InMessageThreads" , "This is the extra received in MessageTheeads " + key);
    }
}
