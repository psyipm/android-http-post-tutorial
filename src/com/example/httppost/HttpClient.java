package com.example.httppost;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
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
public class HttpClient {
  HttpAsyncTask task;
  CallBack callback;

  public HttpClient() {
    this.task = new HttpAsyncTask();
  }

  public void performAsyncPost(String url, StringEntity jsonData, CallBack callback) {
    HttpPost payload = buildRequest(url, jsonData);
    task.setPayload(payload);
    task.execute();
    this.callback = callback;
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
      callback.callback("Data sent!");
    }
  }

  private static String sendPost(HttpPost httpPost) {
    String result = "";

    try {
      org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
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
