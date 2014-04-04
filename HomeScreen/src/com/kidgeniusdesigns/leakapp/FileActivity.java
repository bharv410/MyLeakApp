package com.kidgeniusdesigns.leakapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FileActivity extends Activity {
	EditText fileDataEditText;
	EditText fileNameEditText;
	Button button;
	TextView textView;
	BufferedReader br=null;
	String fileStr, theFileName, returnPath, returnString;
	File path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		fileDataEditText = (EditText)findViewById(R.id.editText1);
		button = (Button)findViewById(R.id.button1);
		fileNameEditText = (EditText)findViewById(R.id.editText2);
		
		textView = (TextView)findViewById(R.id.textView1);
		theFileName = fileNameEditText.getText().toString().concat(".txt");
		path = getFilesDir();
		returnPath =path.getPath();
				
		
	}
	
	
	
	public void writeToFile(View view) {
		fileStr = fileDataEditText.getText().toString();
		fileDataEditText.setEnabled(false);
		
		FileOutputStream outputStream;

		try {
		  outputStream = openFileOutput(theFileName, Context.MODE_PRIVATE);
		  outputStream.write(fileStr.getBytes());
		  outputStream.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
		//File is written to "/data/data/com.kidgeniusdesigns.leakapp/files/"fileStr".txt"
		
	}
	
	public void readTheFile(View view){
	
		try {
			//Reading the file back...
					FileInputStream inputStream = openFileInput(theFileName);
			        InputStreamReader isreader = new InputStreamReader(inputStream);

			        char[] inputBuffer = new char[fileStr.length()];

			        isreader.read(inputBuffer);

			        // Transform the char Array to a String
			        String readString = new String(inputBuffer);

			        // Check if we read back the same chars that we had written out
			        boolean isTheSame = fileStr.equals(readString);
			        textView.setText(readString);
			        Log.i("File Reading stuff", "success = " + isTheSame);

			    } catch (IOException ioe) 
			      {ioe.printStackTrace();}
	}
	
	public static Boolean pathExists(String path, Context ctx)
	{
	    Boolean result = false;
	    String[] pathSeqments = path.split("/");
	    String pathStr = "";
	    for(int i = 0;i<pathSeqments.length;i++ )
	    {
	        pathStr += pathSeqments[i];
	        if(!new File(ctx.getFilesDir() +"/" + pathStr).exists())
	        {
	            result = false;
	            break;
	        }
	        pathStr += "/";
	        result = true;
	    }
	    return result;
	}
}