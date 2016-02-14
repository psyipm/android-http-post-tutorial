package com.example.httppost;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.entity.StringEntity;


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
      HttpClient client = new HttpClient();
      StringEntity jsonData = new StringEntity(person.asJsonString());
      client.performAsyncPost(url, jsonData, new MessageCallback());
    } catch (Exception e) {
      Log.d("JsonParse", e.getLocalizedMessage());
    }
  }

  private class MessageCallback implements CallBack {
    @Override
    public void callback(String... messages) {
      for (String m : messages)
        Toast.makeText(currentActivity.getBaseContext(), m, Toast.LENGTH_LONG).show();
    }
  }
}
