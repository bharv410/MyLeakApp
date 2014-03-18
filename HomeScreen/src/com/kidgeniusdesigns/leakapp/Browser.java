package com.kidgeniusdesigns.leakapp;



import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Browser extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);
		final EditText editText = (EditText) findViewById(R.id.textUri);

		//watch out v
	    //myWebView.getSettings().setJavaScriptEnabled(true);
		
		//urlListen(editText);
		Button goToUrl = (Button)findViewById(R.id.but);
		goToUrl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onKeyboardClick(editText);
            }
        });

		
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
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
		WebView myWebView = (WebView) findViewById(R.id.webView1);
		String mUrl = editText.getText().toString();
		if(mUrl.startsWith("http://")){
			myWebView.loadUrl(mUrl);
		}else{
			myWebView.loadUrl("http://"+ mUrl);
		}

		
	}
}
