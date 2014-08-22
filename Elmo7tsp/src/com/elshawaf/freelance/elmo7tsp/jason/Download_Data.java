package com.elshawaf.freelance.elmo7tsp.jason;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

public class Download_Data extends AsyncTask<String, String, String> {

	static InputStream is = null;
	// url to make request
		// private static String url = "http://api.androidhive.info/alldata/";
		private static String urlmoshrf = "http://almohtasb.com/api/json.ashx?section_no=9995&from_date=2000-06-12&to_date=2013-12-23&count=50";
		private static String urlarticles = "http://almohtasb.com/api/json.ashx?section_no=26&from_date=2000-06-12&to_date=2013-12-25&count=50";
		private static String urlversions = "http://almohtasb.com/api/json.ashx?section_no=8499&from_date=2000-06-12&to_date=2013-12-25&count=50";
		private static String urlmathmeans = "http://almohtasb.com/api/json.ashx?section_no=15993&from_date=2000-06-12&to_date=2013-12-25&count=50";
		private static String urlbookmeans = "http://almohtasb.com/api/json.ashx?section_no=15992&from_date=2000-06-12&to_date=2013-12-25&count=50";
		private static String urldialogs = "http://almohtasb.com/api/json.ashx?section_no=53&from_date=2000-06-12&to_date=2013-12-25&count=50";
		private static String urllib = "http://almohtasb.com/api/json.ashx?section_no=23&from_date=2000-06-12&to_date=2013-12-25&count=50";
		private static String urlnews = "http://almohtasb.com/api/json.ashx?section_no=6495&from_date=2000-06-12&to_date=2013-12-25&count=50";
		private static String [] urls={urlmoshrf,urlarticles,urlversions,urlmathmeans,urlbookmeans,urldialogs,urllib,urlnews}; 
		private static int moshrfid= 9995;
		private static int articlesid=26;
		private static int versionsid=8499;
		private static int mathmeansid=15993;
		private static int bookmeansid=15992;
		private static int urldialogsid=53;
		private static int urllibid=23;
		private static int newsid=6495;
		// JSON Node names
		private static final String TAG_MAIN = "Table";
		private static final String TAG_ID = "id";
		private static final String TAG_DATE = "created";
		private static final String TAG_TITLE = "title";
		private static final String TAG_TEXT = "article_Text";
		SQLiteDatabase db1 = null;
		String TABLE ="Data";
		private static String DBNAME = "Elmo7tsp6.db";
		

		// alldata JSONArray
		JSONArray alldata = null;
		ArrayList<HashMap<String, String>> contactList;

	AsyncTask<String, String, String> a = this;
	Context context;
	ProgressDialog progress;

	public Download_Data(Context context) {
		this.context = context;
		// Database will be created through below method
				createTable();
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
		if(isNetworkAvailable()){

			
			for(int i=0;i<8;i++){
				
				getDataFromJason(urls[i]);
				
			}
			
		}else{
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
		
		return null;

	}

	@Override
	protected void onPostExecute(String result) {
		progress.dismiss();
	//	new Elmo7tspMain().goNext();

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	public void createTable(){
        try{
        db1= context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
		db1.execSQL("CREATE TABLE IF NOT EXISTS  "+TABLE+"(ID INTEGER PRIMARY KEY,SectionID INTEGER ,ElEMENTID INTEGER, TITLE VARCHAR ,DATE VARCHAR,TEXT VARCHAR ); ");
		db1.close();
        }catch(Exception e){
           // Toast.makeText(context, "Error in creating table", Toast.LENGTH_LONG).show();
        }
	}
	public void getDataFromJason(String url) {

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser(context);

		// getting JSON string from URL
	     jParser.getJSONFromUrl(url);

//		 try {
//		 // Getting Array of alldata
//		 alldata = json.getJSONArray(TAG_MAIN);
//		
//		 // looping through All alldata
//		 for(int i = 0; i < alldata.length(); i++){
//		 JSONObject c = alldata.getJSONObject(i);
//		
//		 // Storing each json item in variable
//		 String id = c.getString(TAG_ID);
//		 String date = c.getString(TAG_DATE);
//		 String title = c.getString(TAG_TITLE);
//		 String text = c.getString(TAG_TEXT);
//		db1= context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
//		db1.execSQL("INSERT INTO "+ TABLE +"(SectionID ,ElEMENTID ,TITLE ,DATE,TEXT) VALUES ("+moshrfid+" , "+id+" , '"+title+"', '"+date+"' , '"+text +"')");
//		db1.close();
//		db1= context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
//		Cursor cursor = db1.rawQuery("SELECT * FROM "+TABLE, null);
//		if (cursor != null) {
//			if (cursor.moveToFirst()) {
//				do {
//					// whole data of column is fetched by
//					// getColumnIndex()
//					String TITLE = cursor.getString(cursor
//							.getColumnIndex("TITLE"));
//					String DATE = cursor.getString(cursor
//							.getColumnIndex("DATE"));
//					String TEXT = cursor.getString(cursor
//							.getColumnIndex("TEXT"));
//					System.out.println("Title \n"+TITLE);
//					System.out.println(DATE);
//					System.out.println(TEXT);
//				} while (cursor.moveToNext());
//			}
//		}
//		db1.close();
//		 // creating new HashMap
//		 HashMap<String, String> map = new HashMap<String, String>();
//		
//		 // adding each child node to HashMap key => value
//		 map.put(TAG_ID, id);
//		 map.put(TAG_DATE, date);
//		 map.put(TAG_TITLE, title);
//		 map.put(TAG_TEXT, text);
//		
//		 // adding HashList to ArrayList
//		 contactList.add(map);
//		 }
//		 } catch (JSONException e) {
//		 e.printStackTrace();
//		 }
		
		
		 
	}

}
