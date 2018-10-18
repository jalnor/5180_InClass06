package com.example.gameon.inclass06;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetRequestsAsync extends AsyncTask<String, Integer, JSONObject> {

    final OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    GetJson getJson;


    public GetRequestsAsync(GetJson getJson) {
        this.getJson = getJson;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        getJson.passJson(jsonObject);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        getJson.getProgress(values[0]);
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        Request request;
        Response response;
        JSONObject jo;

        if ( strings[1] == null ) {
            request = new Request.Builder()
                    .url(strings[0])
                    .header("Authorization", strings[2])
                    .build();
        } else {
            RequestBody formBody = new FormBody.Builder()
                    .add("title", strings[1])
                    .build();

            request = new Request.Builder()
                    .url(strings[0])
                    .header("Authorization", strings[2])
                    .post(formBody)
                    .build();
        }

        try {
            response = client.newCall(request).execute();
            //Log.d("oh", "This is response in asynctask " + response.body().string());
            jo = new JSONObject(response.body().string());
            Log.d("oh", "This is jo in asynctask " + jo);
            publishProgress(100);
            return jo;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public interface GetJson {

        public void passJson(JSONObject json);
        public void getProgress(int progress);
    }
}
