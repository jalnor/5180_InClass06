/*
Assignment HW#6
Page MainActivity.java
Authors Jarrod Norris, Abinandaraj Rajendran, Carrie Mao
 */
package com.example.gameon.inclass06;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final EditText emailTV = findViewById(R.id.email);
                final String email = emailTV.getText().toString();

                final EditText passwordTV = findViewById(R.id.password);
                final String password = passwordTV.getText().toString();

                String status = getIntent().getStringExtra("status");



                if ( !isValidEmail(email) && !(password.length() >= 6) )  {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    emailTV.setText("");
                    passwordTV.setText("");
                } else {
                    login(email, password);


                }
            }
        });

        findViewById(R.id.signupBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });



    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void login(String email, String password) {

        final OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();

                        final EditText emailTV = findViewById(R.id.email);
                        final EditText passwordTV = findViewById(R.id.password);

                        emailTV.setText("");
                        passwordTV.setText("");
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();

                                final EditText emailTV = findViewById(R.id.email);
                                final EditText passwordTV = findViewById(R.id.password);

                                emailTV.setText("");
                                passwordTV.setText("");
                            }
                        });
                        throw new IOException("Unexpected code " + response);
                    }

                    Headers responseHeaders = response.headers();
                    // Parses token from JSON
                    String res = responseBody.string();
                    System.out.println(res);

                    JSONObject jo = new JSONObject(res);
                    if ( jo.getString("status").equals("ok") ) {
                        user.setUser_fname(jo.getString("user_fname"));
                        user.setUser_lname(jo.getString("user_lname"));
                        user.setUser_id(jo.getString("user_id"));
                        user.setToken(jo.getString("token"));
                        // Sets shared preferences file
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                                "userPreferences", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("userFName", user.getUser_fname());
                        editor.putString("userLName", user.getUser_lname());
                        editor.putString("userID", user.getUser_id());
                        editor.putString("token", user.getToken());
                        editor.commit();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            // Intent to pass data to MessageThreadsActivity
                            Intent success = new Intent(MainActivity.this, MessageThreadsActivity.class);
                            success.putExtra("Key", user.getToken());
                            success.putExtra("User", user);
                            startActivity(success);
                            finish();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}
