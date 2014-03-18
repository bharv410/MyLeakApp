package com.kidgeniusdesigns.leakapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
//import android.widget.Button;

public class HomeScreen extends Activity {
	public static final String TAG = HomeScreen.class.getSimpleName();
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		final Button toBlog = (Button) findViewById(R.id.blogbutton);
		final Button toWeb = (Button) findViewById(R.id.webbutton);
		}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}
	
	public void toBlog(View view) {
	     Intent intent = new Intent(this, BlogView.class);
	     startActivity(intent);

	     
	 }
	public void toWeb(View view) {
	     Intent intent = new Intent(this, Browser.class);
	     startActivity(intent);
	 }
	
	
	protected static void logException(Exception e) {
		Log.e(TAG, "Exception caught!", e);
	}

	protected static boolean isNetworkAvailable(ConnectivityManager manager) {
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();

		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}

		return isAvailable;
	}
	
}

