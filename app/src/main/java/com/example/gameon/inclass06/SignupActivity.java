package com.example.gameon.inclass06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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


                if ( isValidEmail(e) ) {
                    if (f.isEmpty() || l.isEmpty() || e.isEmpty() || p.isEmpty() || rp.isEmpty()) {
                        Toast.makeText(SignupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    } else if (rp.equals(p)) {
                        signUp(f, l, e, p);
                    } else {
                        Toast.makeText(SignupActivity.this, "Password  mismatch", Toast.LENGTH_SHORT).show();
                        password.setText("");
                        rpassword.setText("");
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                    email.setText("");
                }

//                Toast.makeText(SignupActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();

                findViewById(R.id.b_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(SignupActivity.this, MainActivity.class);
                        finish();

                    }
                });
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void signUp(String firstName, String lastName, String email, String password) {

        final OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("fname", firstName)
                .add("lname", lastName)
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/signup")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(SignupActivity.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_SHORT);
                            }
                        });

                        throw new IOException("Unexpected code " + response);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.login_successful, Toast.LENGTH_SHORT);
                                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                finish();
                            }
                        });
                    }
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }




}


