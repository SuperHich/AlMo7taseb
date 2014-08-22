package com.elshawaf.freelance.elmo7tsp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Elmo7tspMain extends Activity {

	static String formattedDate;
	// private static String urlmoshrf =
	// "http://almohtasb.com/api/json.ashx?section_no=9995&from_date=2000-06-12&to_date=2013-11-28&count=30";
	// private static String urlarticles =
	// "http://almohtasb.com/api/json.ashx?section_no=26&from_date=2000-06-12&to_date="+formattedDate+"&count=20";
	// private static String urlversions =
	// "http://almohtasb.com/api/json.ashx?section_no=8499&from_date=2000-06-12&to_date="+formattedDate+"&count=30";
	// private static String urlmathmeans =
	// "http://almohtasb.com/api/json.ashx?section_no=15993&from_date=2000-06-12&to_date="+formattedDate+"&count=30";
	// private static String urlbookmeans =
	// "http://almohtasb.com/api/json.ashx?section_no=15992&from_date=2000-06-12&to_date="+formattedDate+"&count=30";
	// private static String urldialogs =
	// "http://almohtasb.com/api/json.ashx?section_no=53&from_date=2000-06-12&to_date="+formattedDate+"&count=20";
	// private static String urllib =
	// "http://almohtasb.com/api/json.ashx?section_no=23&from_date=2000-11-12&to_date="+formattedDate+"&count=20";
	// private static String urlnews =
	// "http://almohtasb.com/api/json.ashx?section_no=6495&from_date=2000-06-12&to_date="+formattedDate+"&count=30";
	// private static String[] urls = { urlmoshrf, urlarticles, urlversions,
	// urlmathmeans, urlbookmeans, urldialogs, urllib, urlnews };
	private static int moshrfid = 9995;
	private static int articlesid = 26;
	private static int versionsid = 8499;
	private static int mathmeansid = 15993;
	private static int bookmeansid = 15992;
	private static int fileUrl = 12111;
	private static int urldialogsid = 53;
	private static int urllibid = 23;
	private static int newsid = 6495;
	private static int sec_id = 0;
	// JSON Node names
	private static final String TAG_MAIN = "Table";
	private static final String TAG_ID = "id";
	private static final String TAG_DATE = "created";
	private static final String TAG_TITLE = "title";
	private static final String TAG_TEXT = "article_Text";
	SQLiteDatabase db1 = null;
	String TABLE = "Data";
	private static String DBNAME = "Elmohtsp3.db";
	public JSONObject jObj = null;

	// alldata JSONArray
	JSONArray alldata = null;
	boolean flag = false;
	int no = 2;
	ProgressBar pb;
	JSONParse jParser;
	TextView downloads;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.elmo7tsp_main);
		// pb = (ProgressBar) findViewById(R.id.bar);

		new CountDownTimer(2000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				goNext();
			}
		}.start();

		// downloads = (TextView) findViewById(R.id.txtdownload);
		//
		// Calendar c = Calendar.getInstance();
		// System.out.println("Current time => " + c.getTime());
		//
		// formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(new
		// Date());
		// System.out.println(formattedDate);
		// startDownlod(1, formattedDate);
		// Handler handler2 = new Handler();
		// handler2.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// goNext();
		//
		// }
		// }, 120000);
	}

	public void startDownlod(int no, String curDate) {
		if (isNetworkAvailable()) {

			// check what section is to get url?'
			System.out.println("Staaaaaaaaaaatus " + isNetworkAvailable());
			if (no == 1) {

				TABLE = "moshrfData";
				createTable(TABLE);
				sec_id = 9995;
				String urlmoshrf = "http://almohtasb.com/api/json.ashx?section_no=9995&from_date=2000-06-12&to_date="
						+ curDate + "&count=30";
				getDataFromJason(urlmoshrf);
				downloads.setText(" جارى التحميل  (10%)");

			} else if (no == 2) {

				TABLE = "newsData";
				createTable(TABLE);
				sec_id = 6495;
				String urlnews = "http://almohtasb.com/api/json.ashx?section_no=6495&from_date=2000-06-12&to_date="
						+ curDate + "&count=30";

				getDataFromJason(urlnews);
				downloads.setText(" جارى التحميل  (20%)");
			} else if (no == 3) {
				TABLE = "artData";

				createTable(TABLE);
				sec_id = 26;
				String urlarticles = "http://almohtasb.com/api/json.ashx?section_no=26&from_date=2000-06-12&to_date="
						+ curDate + "&count=30";

				getDataFromJason(urlarticles);
				downloads.setText(" جارى التحميل  (50%)");
			} else if (no == 4) {

				TABLE = "dialogsData";
				createTable(TABLE);
				sec_id = 53;
				String urldialogs = "http://almohtasb.com/api/json.ashx?section_no=53&from_date=2000-06-12&to_date="
						+ curDate + "&count=30";

				getDataFromJason(urldialogs);
				downloads.setText(" جارى التحميل  (60%)");
			} else if (no == 5) {

				TABLE = "libData";
				sec_id = 23;
				createTable(TABLE);
				String urllib = "http://almohtasb.com/api/json.ashx?section_no=23&from_date=2000-11-12&to_date="
						+ curDate + "&count=30";

				getDataFromJason(urllib);
				downloads.setText(" جارى التحميل  (70%)");
			} else if (no == 6) {

				TABLE = "meansData";
				sec_id = 15993;
				createTable(TABLE);
				String urlmathmeans = "http://almohtasb.com/api/json.ashx?section_no=15993&from_date=2000-06-12&to_date="
						+ curDate + "&count=40";

				getDataFromJason(urlmathmeans);
				downloads.setText(" جارى التحميل  (80%)");
			} else if (no == 7) {

				TABLE = "bookData";
				sec_id = 15992;
				createTable(TABLE);
				String urlbookmeans = "http://almohtasb.com/api/json.ashx?section_no=15992&from_date=2000-06-12&to_date="
						+ curDate + "&count=30";

				getDataFromJason(urlbookmeans);
				downloads.setText(" جارى التحميل  (95%)");
			} else if (no == 8) {
				TABLE = "file-url";
				sec_id = fileUrl;
				createTable(TABLE);
				String urlbookmeans = "http://almohtasb.com/api/json.ashx?section_no=‫‪12111&from_date=2000-06-12&to_date="
						+ curDate + "&count=30";

				getDataFromJason(urlbookmeans);
				downloads.setText(" جارى التحميل  (95%)");
			}

		} else {

			// // check what section is to get url?
			// if (no == 1) {
			TABLE = "moshrfData";
			try {
				db1 = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
				Cursor cursor = db1.rawQuery("SELECT * FROM  " + TABLE, null);
				if (cursor != null) {
					if (cursor.moveToFirst() && cursor.getCount() > 0) {

						do {
							// whole data of column is fetched by
							// getColumnIndex()
							String TITLE = cursor.getString(cursor
									.getColumnIndex("TITLE"));
							String DATE = cursor.getString(cursor
									.getColumnIndex("DATE"));
							String TEXT = cursor.getString(cursor
									.getColumnIndex("TEXT"));
							System.out.println("Title \n" + TITLE);

						} while (cursor.moveToNext());
					}
				}
			} catch (SQLiteException e) {
				if (e.getMessage().toString().contains("no such table")) {
					Log.e("ERROR", "Creating table " + TABLE
							+ "because it doesn't exist!");
					Toast.makeText(Elmo7tspMain.this,
							"لا توجد معلومات و قم بالاتصال بالانترنت",
							Toast.LENGTH_LONG).show();

				}
			}
			Handler han = new Handler();
			han.postDelayed(new Runnable() {

				@Override
				public void run() {
					goNext();

				}
			}, 2000);

		}

	}

	public void goNext() {

		Intent intent = new Intent(Elmo7tspMain.this, MainList.class);
		intent.putExtra("isFirstVisit", true);
		startActivity(intent);
		finish();

	}

	public void createTable(String tablename) {

		try {
			db1 = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
			db1.execSQL("DROP TABLE IF EXISTS " + tablename);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			db1.close();
		}
		try {
			db1 = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
			db1.execSQL("CREATE TABLE IF NOT EXISTS  "
					+ tablename
					+ "(ID INTEGER PRIMARY KEY,SectionID INTEGER ,ElEMENTID INTEGER, TITLE VARCHAR ,DATE VARCHAR,TEXT VARCHAR ); ");
		} catch (Exception e) {
			// Toast.makeText(context, "Error in creating table",
			// Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} finally {
			db1.close();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (flag == true) {
			jParser.cancel(true);
		}
	}

	public void getDataFromJason(String url) {

		// Creating JSON Parser instance
		jParser = new JSONParse(this);

		// getting JSON string from URL
		jParser.execute(url);

		JSONObject json = jObj;
		flag = true;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private class JSONParse extends AsyncTask<String, String, String> {

		AsyncTask<String, String, String> a = this;
		Context context;
		ProgressDialog progress;
		InputStream is = null;

		String json = "";
		private String filenamemoshrf = "Jasondatamoshrf";
		private String filenamearticles = "Jasondataarticles";
		private String filenameversions = "Jasondataversions";
		private String filenamemathmeans = "Jasondatamathmeans";
		private String filenamebookmeans = "Jasondatabookmeans";
		private String filenamedialogs = "Jasondatadialogs";
		private String filenamelib = "Jasondatalib";
		private String filenamenews = "Jasondatanews";
		private String[] names = { filenamemoshrf, filenamearticles,
				filenameversions, filenamemathmeans, filenamebookmeans,
				filenamedialogs, filenamelib, filenamenews };
		private String filepath = "MyFileStorage";

		public JSONParse(Context con) {
			context = con;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			formattedDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date());
			if (no < 6) {
				startDownlod(no, formattedDate);
				no++;
			} else {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// Intent intent = new Intent(Elmo7tspMain.this,
						// MainList.class);
						// startActivity(intent);
						// finish();

					}
				});

			}

		}

		@Override
		protected String doInBackground(String... params) {

			// Making HTTP request
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(params[0]);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");

				}
				is.close();
				if (!sb.equals("لا توجد مواد في هذا القسم")) {

					json = sb.toString();
					System.out.println("the parsing data is : " + json);

					// try parse the string to a JSON object
					try {
						jObj = new JSONObject(json);
					} catch (JSONException e) {
						// Log.e("JSON Parser",
						// "Error parsing data " + e.toString());
						e.printStackTrace();
					}
					try {
						// Getting Array of alldata
						if (jObj != null) {
							// System.out
							// .println("errrrrrrrrrrrrrrrrrrrrrrrrrrrrrorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
							//
							// } else {
							alldata = jObj.getJSONArray(TAG_MAIN);

							// looping through All alldata
							for (int i = 0; i < alldata.length(); i++) {
								JSONObject c = alldata.getJSONObject(i);

								// Storing each json item in variable
								String id = c.getString(TAG_ID);
								String date = c.getString(TAG_DATE);
								String title = c.getString(TAG_TITLE);
								String text = c.getString(TAG_TEXT);

								System.out.println("Title + \n" + title);

								db1 = openOrCreateDatabase(DBNAME,
										Context.MODE_PRIVATE, null);
								db1.execSQL("INSERT INTO "
										+ TABLE
										+ "(SectionID ,ElEMENTID ,TITLE ,DATE,TEXT) VALUES ("
										+ moshrfid + " , " + id + " , '"
										+ title + "', '" + date + "' , '"
										+ text + "')");
								db1.close();
								db1 = openOrCreateDatabase(DBNAME,
										Context.MODE_PRIVATE, null);
								Cursor cursor = db1.rawQuery("SELECT * FROM "
										+ TABLE, null);
								if (cursor != null) {
									if (cursor.moveToFirst()) {
										do {
											// whole data of column is fetched
											// by
											// getColumnIndex()
											String TITLE = cursor
													.getString(cursor
															.getColumnIndex("TITLE"));
											String DATE = cursor
													.getString(cursor
															.getColumnIndex("DATE"));
											String TEXT = cursor
													.getString(cursor
															.getColumnIndex("TEXT"));
											System.out.println("Title \n"
													+ TITLE);

										} while (cursor.moveToNext());
									}
								}
								db1.close();

							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				// Log.e("Buffer Error", "Error converting result " +
				// e.toString());
				e.printStackTrace();
			}

			return null;
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

			// progress.show();
		}
	}
}
