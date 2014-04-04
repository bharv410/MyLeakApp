package com.kidgeniusdesigns.leakapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BlogView extends ListActivity {
	
	public static final int NUMBER_OF_POSTS = 20;
	protected JSONObject mBlogData;
	public static final String TAG = BlogView.class.getSimpleName();
	protected ProgressBar mProgressBar;
	private final String KEY_TITLE = "title";
	private final String KEY_AUTHOR = "author";
	private HashMap<Integer, String> urls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		urls = new HashMap<Integer, String>();
		setContentView(R.layout.activity_blog_view);
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		if (HomeScreen.isNetworkAvailable(manager)) {
			mProgressBar.setVisibility(View.VISIBLE);
			GetBlogPostsTask getBlogPostsTask = new GetBlogPostsTask();
			getBlogPostsTask.execute();
		} else {
			Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG)
					.show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blog_view, menu);
		return true;
	}
	protected void onKeyboardClick(EditText editText) {
		String siteUrl = editText.getText().toString();

		Intent intent = new Intent(this, Browser.class);
		intent.setData(Uri.parse(siteUrl));
		startActivity(intent);
	}

	public void handleBlogResponse() {
		mProgressBar.setVisibility(View.INVISIBLE);

		if (mBlogData == null) {
			updateDisplayForError();
		} else {
			try {
				JSONArray jsonPosts = mBlogData.getJSONArray("posts");
				ArrayList<HashMap<String, String>> blogPosts = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < jsonPosts.length(); i++) {
					JSONObject post = jsonPosts.getJSONObject(i);
					String title = post.getString(KEY_TITLE);
					title = Html.fromHtml(title).toString();
					String author = post.getString(KEY_AUTHOR);
					author = Html.fromHtml(author).toString();
					
					
					String url =post.getString("url");
					urls.put(i, url);
					HashMap<String, String> blogPost = new HashMap<String, String>();
					blogPost.put(KEY_TITLE, title);
					blogPost.put(KEY_AUTHOR, author);

					blogPosts.add(blogPost);
				}

				String[] keys = { KEY_TITLE, KEY_AUTHOR };
				int[] ids = { android.R.id.text1, android.R.id.text2 };
				SimpleAdapter adapter = new SimpleAdapter(this, blogPosts,
						android.R.layout.simple_list_item_2, keys, ids);

				setListAdapter(adapter);
			} catch (JSONException e) {
				HomeScreen.logException(e);
			}
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, Browser.class);
		intent.putExtra("url", urls.get(position));
		startActivity(intent);
	}
	
	private void updateDisplayForError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.error_title));
		builder.setMessage(getString(R.string.error_message));
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();

		TextView emptyTextView = (TextView) getListView().getEmptyView();
		emptyTextView.setText(getString(R.string.no_items));
	}
	
	private class GetBlogPostsTask extends AsyncTask<Object, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... arg0) {
			int responseCode = -1;
			JSONObject jsonResponse = null;

			try {
				URL blogFeedUrl = new URL(
						"http://blog.teamtreehouse.com/api/get_recent_summary/?count="
								+ NUMBER_OF_POSTS);
				HttpURLConnection connection = (HttpURLConnection) blogFeedUrl
						.openConnection();
				connection.connect();

				responseCode = connection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream inputStream = connection.getInputStream();
					Reader reader = new InputStreamReader(inputStream);
					int contentLength = connection.getContentLength();
					char[] charArray = new char[contentLength];
					reader.read(charArray);
					String responseData = new String(charArray);

					jsonResponse = new JSONObject(responseData);
				} else {
					Log.i(TAG, "Unsuccessful HTTP Response Code: "
							+ responseCode);
				}
			} catch (MalformedURLException e) {
				HomeScreen.logException(e);
			} catch (IOException e) {
				HomeScreen.logException(e);
			} catch (Exception e) {
				HomeScreen.logException(e);
			}

			return jsonResponse;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			mBlogData = result;
			handleBlogResponse();
		}

	}
}
