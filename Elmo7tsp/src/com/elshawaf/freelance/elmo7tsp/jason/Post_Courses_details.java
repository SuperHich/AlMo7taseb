package com.elshawaf.freelance.elmo7tsp.jason;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;


public class Post_Courses_details extends AsyncTask<String, String, String> {
	private HashMap<String, String> mData = null;// post data

	/**
	 * constructor
	 */
	AsyncTask<String, String, String> a = this;
	Context context;
	ProgressDialog progress;
	String str = "";
	String Courses_names = "";
	String Courses_desc = "";
	String Courses_start = "";
	String Courses_end = "";
	Boolean Courses_active = false;
	String Courses_request = "";
	List<String> Courses_instructors = new ArrayList<String>();

	public Post_Courses_details(HashMap<String, String> data, Context context) {
		mData = data;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		// Show progress Dialog here
		super.onPreExecute();

		// create ProgressDialog here ...
		progress = new ProgressDialog(context);
		progress.setMessage("Processing , please wait ... ");
		progress.setCancelable(true);
		// set other progressbar attributes
		progress.show();

	}

	@Override
	protected String doInBackground(String... params) {
		byte[] result = null;
		progress.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				Toast.makeText(context, "You cancelled the process",
						Toast.LENGTH_LONG).show();
				a.cancel(true);
			}
		});
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(params[0]);// in this case, params[0] is
												// URL
		try {
			// set up post data
			ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			Iterator<String> it = mData.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				nameValuePair.add(new BasicNameValuePair(key, mData.get(key)));
			}
			post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
			HttpResponse response = client.execute(post);
			//
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpURLConnection.HTTP_OK) {
				result = EntityUtils.toByteArray(response.getEntity());
				str = new String(result, "UTF-8");
			}
			if (isNetworkAvailable()) {
				JSONObject kl;
				try {
					kl = new JSONObject(str);

					JSONArray courses = kl.getJSONArray("course_details");
					for (int i = 0; i < courses.length(); i++) {
						kl = courses.getJSONObject(i);
						Courses_names = kl.getString("course_name");
						Courses_desc = kl.getString("course_desc");
						Courses_start = kl.getString("course_star_date");
						Courses_end = kl.getString("course_end_date");
						Courses_request = kl.getString("course_Approved");
						if (kl.getInt("course_isAssign") == 1)
							Courses_active = true;
						else
							Courses_active = false;
						JSONArray ins = kl.getJSONArray("course_instructor");
						for (int j = 0; j < ins.length(); j++) {
							JSONObject instructor = ins.getJSONObject(j);
							Courses_instructors.add(instructor
									.getString("teacher_name"));
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(context, "Check your internet connection ",
						Toast.LENGTH_LONG).show();
			}
			// //
		} catch (Exception e) {
		}

		return str;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/**
	 * on getting result
	 */
	@Override
	protected void onPostExecute(String result) {
		
		progress.dismiss();
	}
}
