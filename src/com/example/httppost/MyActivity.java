package com.example.httppost;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyActivity extends Activity implements OnClickListener {
  TextView tvIsConnected;
  EditText etName, etCountry, etTwitter;
  Button btnPost;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    getReferencesToViews();
    showConnectionInformation();

    // add click listener to button post
    btnPost.setOnClickListener(this);
  }

  private void getReferencesToViews() {
    tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
    etName = (EditText) findViewById(R.id.etName);
    etCountry = (EditText) findViewById(R.id.etCountry);
    etTwitter = (EditText) findViewById(R.id.etTwitter);
    btnPost = (Button) findViewById(R.id.btnPost);
  }

  private void showConnectionInformation() {
    if(isConnected()) {
      tvIsConnected.setBackgroundColor(0xFF00CC00);
      tvIsConnected.setText("You are connected");
    }
    else {
      tvIsConnected.setText("You are NOT connected");
    }
  }

  private boolean isConnected() {
    ConnectivityManager manager = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = manager.getActiveNetworkInfo();

    return netInfo != null && netInfo.isConnected();
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btnPost:
        persistPerson();
        break;
    }
  }

  private void persistPerson() {
    Person person = new Person();
    person.setName(etName.getText().toString());
    person.setCountry(etCountry.getText().toString());
    person.setTwitter(etTwitter.getText().toString());

    new RemotePerson(person, this).persist();
  }
}
