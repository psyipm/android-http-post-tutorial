package com.example.httppost;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ipm on 14.02.16.
 */
public class Person {
  private String name;
  private String country;
  private String twitter;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getTwitter() {
    return twitter;
  }

  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  public boolean isValid() {
    return !(name.trim().equals("") || country.trim().equals("") || twitter.trim().equals(""));
  }

  public String asJsonString() throws JSONException {
    return asJsonObject().toString();
  }

  private JSONObject asJsonObject() throws JSONException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.accumulate("name", getName());
    jsonObject.accumulate("country", getCountry());
    jsonObject.accumulate("twitter", getTwitter());

    return jsonObject;
  }
}
