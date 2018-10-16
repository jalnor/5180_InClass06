package com.example.gameon.inclass06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MessageThreadsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);
        final OkHttpClient client = new OkHttpClient();

        String key = getIntent().getStringExtra("Key");
        String firstName = getIntent().getStringExtra("FirstName");
        String lastName = getIntent().getStringExtra("LastName");

        TextView user = findViewById(R.id.userName);
        String username = firstName + " " + lastName;
        user.setText(username);
        String n = "BEARER " + key;

        Log.d("InMessageThreads", "THis is concatenated string " + n);

        Log.d("InMessageThreads" , "This is the extra received in MessageTheeads " + key);

//        RequestBody formBody = new FormBody.Builder()
//                .add("Authorization", n)
//                .build();

        Request request = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread")
                .header("Authorization", n)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Toast.makeText(MessageThreadsActivity.this, "Thread retrieval failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();

                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    //System.out.println(responseBody.string());
                    // Parses token from JSON
                    String res = responseBody.string();
                    JSONObject jo = new JSONObject(res);
                    for ( int i = 0; i < jo.length(); i++ ) {

                    }
//                    String key = jo.getString("token");
                    // Intent to pass data to MessageThreadsActivity
//                    Intent success = new Intent(MessageThreadsActivity.this, MessageThreadsActivity.class);
//                    success.putExtra("Key",  key);
//                    startActivity(success);

                }catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.addnewthreadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().removeExtra("Key");
            }
        });
    }
}
