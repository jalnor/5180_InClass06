package com.example.gameon.inclass06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

    ArrayList<Threads> t = new ArrayList<>();
    ArrayList<String> strs = new ArrayList<>();
    Threads threads = new Threads();
    String postBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);
        final OkHttpClient client = new OkHttpClient();

        final String key = getIntent().getStringExtra("Key");
        final String firstName = getIntent().getStringExtra("FirstName");
        final String lastName = getIntent().getStringExtra("LastName");

        TextView user = findViewById(R.id.userName);
        String username = firstName + " " + lastName;
        user.setText(username);
        final String n = "BEARER " + key;

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

                    // Parses token from JSON
                    String res = responseBody.string();
                    JSONObject jo = new JSONObject(res);
                    JSONArray info = jo.getJSONArray("threads");


                    for ( int i = 0; i < info.length(); i++ ) {
                        //Log.d("InMessageThreads", "The results in JSONArray are : " + info);
                    }


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
                EditText newThreads = findViewById(R.id.newThread);

                postBody = newThreads.getText().toString();
                Log.d("InMessageThreads", "This is the postBody " + postBody);
                RequestBody formBody = new FormBody.Builder()
                        .add("title", postBody)
                        .build();

                Request request = new Request.Builder()
                        .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/add")
                        .header("Authorization", n)
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(MessageThreadsActivity.this, "Thread creation failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("InMessageThreads", "This is the response from server " + response);
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful())
                                throw new IOException("Unexpected code " + response);
                            //Log.d("ohmy", "This is the name " + responseBody.string());
                            String served = responseBody.string();
                            JSONObject json = new JSONObject(served);
                            JSONObject j = json.getJSONObject("thread");

                            Log.d("ohmy", "The jsonobject " + j);
                            threads.setUfname(j.getString("user_fname"));
                            threads.setUlname(j.getString("user_lname"));
                            threads.setUid(j.getString("user_id"));
                            threads.setId(j.getString("id"));
                            threads.setTitle(j.getString("title"));
                            threads.setCreatedAt(j.getString("created_at"));
                            Log.d("ohmy", "This is threads " + threads.toString());
                            t.add(threads);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                newThreads.setText("");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addThreads();
            }
        });

        ListView list = findViewById(R.id.lv);




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Threads current = t.get(position);
                String url = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/delete/" + current.getId();

                Request request = new Request.Builder()
                        .url(url)
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(MessageThreadsActivity.this, "Thread creation failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful())
                                throw new IOException("Unexpected code " + response);
                            Log.d("ohmy", "This is response in delete " + responseBody.string());

                        }
                    }
                });

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

    public void addThreads() {

        ListView listView = findViewById(R.id.lv);
        Log.d("ohmy", "This is the listview or at least we made iot here I think " + listView + " thread " + this.t.get(0).toString());
        ThreadAdapter adapter = new ThreadAdapter(this, R.layout.thread_card, this.t);
        // ArrayAdapter<String> adapter = new ArrayAdapter<>(MessageThreadsActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, strs);
        listView.setAdapter(adapter);
    }




}
