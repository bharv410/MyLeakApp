package com.kidgeniusdesigns.leakapp;



import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Browser extends Activity implements OnClickListener {

	EditText editText;
	WebView myWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);
		editText = (EditText) findViewById(R.id.textUri);
		editText.setOnClickListener(this);
		myWebView=(WebView)findViewById(R.id.webView1);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    myWebView.loadUrl(extras.getString("url"));
		}else{
		myWebView.loadUrl("http://www.google.com");
		}


		Button goToUrl = (Button)findViewById(R.id.but);
		goToUrl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onKeyboardClick(editText);
            }
        });

		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
	}
	@Override
	public void onResume() {
	    super.onResume();

	    TimerTask tt = new TimerTask() {

	        @Override
	        public void run() {
	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	        }
	    };

	    final Timer timer = new Timer();
	    timer.schedule(tt, 200);
	}
	
	protected void urlListen(final EditText editText) {
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				onKeyboardClick(editText);
				return false;
			}
		});
	}
	
	protected void onKeyboardClick(EditText editText) {
		String mUrl = editText.getText().toString();
		if(mUrl.startsWith("http://")){
			myWebView.loadUrl(mUrl);
		}else{
			myWebView.loadUrl("http://"+ mUrl);
		}

		
	}

	@Override
	public void onClick(View arg0) {
		editText.setText("");		
	}
}
