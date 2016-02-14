package com.example.httppost;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ipm on 14.02.16.
 */
public class RemotePerson {
  Person person;
  Activity currentActivity;

  public RemotePerson(Person person, Activity currentActivity) {
    this.person = person;
    this.currentActivity = currentActivity;
  }

  public void persist() {
    if(!person.isValid()) {
      Toast.makeText(currentActivity.getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
      return;
    }

    String url = "http://hmkcode.appspot.com/jsonservlet";
    try {
      HttpPost payload = buildRequest(url, new StringEntity(person.asJsonString()));
      HttpAsyncTask task = new HttpAsyncTask();
      task.setPayload(payload);
      task.execute();
    } catch (Exception e) {
      Log.d("JsonParse", e.getLocalizedMessage());
    }
  }

  private HttpPost buildRequest(String url, StringEntity entity) {
    HttpPost payload = new HttpPost(url);
    payload.setEntity(entity);
    payload.setHeader("Accept", "application/json");
    payload.setHeader("Content-type", "application/json");

    return payload;
  }

  private class HttpAsyncTask extends AsyncTask<String, Void, String> {
    HttpPost payload;

    public void setPayload(HttpPost payload) {
      this.payload = payload;
    }

    @Override
    protected String doInBackground(String... urls) {
      return sendPost(payload);
    }

    @Override
    protected void onPostExecute(String result) {
      Toast.makeText(currentActivity.getBaseContext(), "Data sent!", Toast.LENGTH_LONG).show();
    }
  }

  private static String sendPost(HttpPost httpPost) {
    String result = "";

    try {
      HttpClient httpClient = new DefaultHttpClient();
      HttpResponse response = httpClient.execute(httpPost);

      result = getResponseString(response);
    } catch (Exception e) {
      Log.d("InputStream", e.getLocalizedMessage());
    }

    return result;
  }

  private static String getResponseString(HttpResponse response) throws IOException {
    InputStream inputStream;
    inputStream = response.getEntity().getContent();

    if(inputStream != null) {
      return convertInputStreamToString(inputStream);
    }
    else {
      return "Did not work";
    }
  }

  private static String convertInputStreamToString(InputStream inputStream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String line;
    String result = "";
    while ((line = reader.readLine()) != null) {
      result += line;
    }

    inputStream.close();
    return result;
  }
}
