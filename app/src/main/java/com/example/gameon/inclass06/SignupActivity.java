package com.example.gameon.inclass06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final OkHttpClient client = new OkHttpClient();


        findViewById(R.id.b_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText fname, lname, email, password, rpassword;
                Button signup;
                fname = findViewById(R.id.et_firstname);
                lname = findViewById(R.id.et_lastname);
                email = findViewById(R.id.et_email);
                password = findViewById(R.id.et_password);
                rpassword = findViewById(R.id.et_rpassword);
                signup = findViewById(R.id.b_signup);
                String f = fname.getText().toString();
                String l = lname.getText().toString();
                String e = email.getText().toString();
                String p = password.getText().toString();
                String rp = rpassword.getText().toString();

                if (rp.equals(p)) {
                    RequestBody formBody = new FormBody.Builder()
                            .add("fname", f)
                            .add("lname", l)
                            .add("email", e)
                            .add("password", p)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/signup")
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try (ResponseBody responseBody = response.body()) {
                                if (!response.isSuccessful())
                                    throw new IOException("Unexpected code " + response);

                                Headers responseHeaders = response.headers();
                                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                                }
                                Log.d("message", "The response is " + responseHeaders);
                                System.out.println(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignupActivity.this, "Password  mismatch", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.b_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                finish();

            }
        });
    }




}
