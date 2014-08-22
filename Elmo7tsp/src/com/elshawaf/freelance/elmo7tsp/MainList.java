package com.elshawaf.freelance.elmo7tsp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("NewApi")
public class MainList extends Activity {

	ListView mainlist;
	ImageButton facebook, twitter, youtube;
	static String formattedDate;
	SQLiteDatabase db1 = null;
	String TABLE = "Data";
	private static String DBNAME = "Elmohtsp3.db";
	private static int moshrfid = 16929;
	private static int articlesid = 16751;
	private static int versionsid = 8499;
	private static int mathmeansid = 15993;
	private static int bookmeansid = 15992;
	private static int fileUrl = 12111;
	private static int urldialogsid = 16753;
	private static int urllibid = 16752;
	private static int newsid = 16728;
	private static int sec_id = 0;
	// JSON Node names
	private static final String TAG_MAIN = "Table";
	private static final String TAG_ID = "id";
	private static final String TAG_DATE = "created";
	private static final String TAG_TITLE = "title";
	private static final String TAG_TEXT = "article_Text";
	private static final String TAG_URL = "file_url";

	public JSONObject jObj = null;

	// alldata JSONArray
	JSONArray alldata = null;
	boolean flag = false;
	int no = 2;
	ProgressBar pb;
    boolean updateable = true;
	JSONParse jParser;
	boolean isFirstVisit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		ArrayList<ItemDetails> list_details = getListContent();

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		System.out.println(formattedDate);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			isFirstVisit = bundle.getBoolean("isFirstVisit");
		}

		// Handler handler2 = new Handler();
		// handler2.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// //goNext();
		//
		// }
		// }, 120000);

		facebook = (ImageButton) findViewById(R.id.fb);
		twitter = (ImageButton) findViewById(R.id.twitter);
		youtube = (ImageButton) findViewById(R.id.yt);
		facebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("https://www.facebook.com/almohtasb1?fref=ts"));
				startActivity(browserIntent);

			}
		});
		twitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("https://twitter.com/@al_mohtasb1"));
				startActivity(browserIntent);

			}
		});
		youtube.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://www.youtube.com/user/almohtasb1"));
				startActivity(browserIntent);

			}
		});

		if (isFirstVisit) {
			isFirstVisit = false;

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("هل تريد تحديث التطبيق؟");

			// set dialog message
			alertDialogBuilder
					.setMessage("قم بالضغط على نعم للتحديث.")
					.setCancelable(false)
					.setPositiveButton("نعم",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, close
									// current activity
									isFirstVisit = true;
                                    updateable = true;
								}
							})
					.setNegativeButton("ﻻ",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									isFirstVisit = false;
                                    updateable = false;
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
		mainlist = (ListView) findViewById(R.id.lv);
		mainlist.setAdapter(new MyAdapter(this, list_details));

		mainlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Object o = mainlist.getItemAtPosition(position);
				ItemDetails obj_itemDetails = (ItemDetails) o;
				String discr = obj_itemDetails.getItemDescription();
				String selectedname = null;
				int selectedimage = 0;
                boolean updateStauts = true ;
				if (discr.equals("moshrf")) {
					selectedimage = R.drawable.pic13;
					selectedname = "ركن المشرف";
                    updateStauts = updateable;
					Intent intent = new Intent(MainList.this,
							ListOfTitles.class);
					intent.putExtra("txt", selectedname);
					intent.putExtra("img", selectedimage);
					intent.putExtra("update", updateStauts);

					startActivity(intent);
					//finish();

				} else if (discr.equals("news")) {
					selectedimage = R.drawable.pic6;
					selectedname = "الاخبار";
                    updateStauts = updateable;
					// startDownlod(2, formattedDate);
					Intent intent = new Intent(MainList.this,
							ListOfTitles.class);
					intent.putExtra("txt", selectedname);
					intent.putExtra("img", selectedimage);
                    intent.putExtra("update", updateStauts);
					startActivity(intent);
					//finish();

				} else if (discr.equals("articles")) {
					selectedimage = R.drawable.pic3;
					selectedname = "المقالات";
                    updateStauts = updateable;
					// startDownlod(3, formattedDate);
					Intent intent = new Intent(MainList.this,
							ListOfTitles.class);
					intent.putExtra("txt", selectedname);
					intent.putExtra("img", selectedimage);
                    intent.putExtra("update", updateStauts);
					startActivity(intent);

				} else if (discr.equals("dialogs")) {
					selectedimage = R.drawable.pic2;
					selectedname = "الحوارات";
                    updateStauts = updateable;
					// startDownlod(4, formattedDate);
					Intent intent = new Intent(MainList.this,
							ListOfTitles.class);
					intent.putExtra("txt", selectedname);
					intent.putExtra("img", selectedimage);
                    intent.putExtra("update", updateStauts);
					startActivity(intent);
					//finish();

				} else if (discr.equals("lib")) {
					selectedimage = R.drawable.pic4;
					selectedname = "مكتبة المحتسب";
                    updateStauts = updateable;
					// startDownlod(5, formattedDate);
					Intent intent = new Intent(MainList.this,
							ListOfTitles.class);
					intent.putExtra("txt", selectedname);
					intent.putExtra("img", selectedimage);
                    intent.putExtra("update", updateStauts);
					startActivity(intent);
					//finish();

				} else if (discr.equals("files")) {
					selectedimage = R.drawable.pic5;
					selectedname = "ملفات الموقع";
                    updateStauts = updateable;
					// startDownlod(8, formattedDate);
					Intent intent = new Intent(MainList.this,
							SiteFilesActivity.class);
					intent.putExtra("txt", selectedname);
					intent.putExtra("img", selectedimage);
                    intent.putExtra("update", updateStauts);
					startActivity(intent);
					//finish();

				} else if (discr.equals("versions")) {
					selectedimage = R.drawable.pic7;
					selectedname = "اصدارتنا";
                    updateStauts = updateable;
					// startDownlod(7, formattedDate);
					Intent intent = new Intent(MainList.this,
							VersionsChoose.class);
					intent.putExtra("txt", selectedname);
					intent.putExtra("img", selectedimage);
                    intent.putExtra("update", updateStauts);
					startActivity(intent);
					//finish();

				} else if (discr.equals("encyclopedia")) {
					selectedimage = R.drawable.hesba;
					selectedname = "موسوعة الحسبه";
                    updateStauts = updateable;
					// startDownlod(8, formattedDate);
					Intent intent = new Intent(MainList.this,
							EncyclopediaActivity.class);
					intent.putExtra("txt", selectedname);
					intent.putExtra("img", selectedimage);
                    intent.putExtra("update", updateStauts);
					startActivity(intent);
					//finish();

				} else if (discr.equals("about")) {
					selectedimage = R.drawable.ic_launcher;
					selectedname = "عن التطبيق";
                    updateStauts = updateable;
					// startDownlod(8, formattedDate);
					Intent intent = new Intent(MainList.this,
							AboutActivity.class);
					intent.putExtra("txt", selectedname);
					intent.putExtra("img", selectedimage);
                    intent.putExtra("update", updateStauts);

					startActivity(intent);
					//finish();

				}

			}
		});
	}

	public ArrayList<ItemDetails> getListContent() {
		ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();
		ItemDetails itemdetail = new ItemDetails();
		itemdetail.setImageNumber(1);
		itemdetail.setName("ركن المشرف");
		itemdetail.setItemDescription("moshrf");
		results.add(itemdetail);

		itemdetail = new ItemDetails();
		itemdetail.setImageNumber(2);
		itemdetail.setName("الاخبار");
		itemdetail.setItemDescription("news");
		results.add(itemdetail);

		itemdetail = new ItemDetails();
		itemdetail.setImageNumber(3);
		itemdetail.setName("المقالات");
		itemdetail.setItemDescription("articles");
		results.add(itemdetail);

		itemdetail = new ItemDetails();
		itemdetail.setImageNumber(4);
		itemdetail.setName("الحوارات");
		itemdetail.setItemDescription("dialogs");
		results.add(itemdetail);

		itemdetail = new ItemDetails();
		itemdetail.setImageNumber(5);
		itemdetail.setName("مكتبة المحتسب");
		itemdetail.setItemDescription("lib");
		results.add(itemdetail);

		itemdetail = new ItemDetails();
		itemdetail.setImageNumber(6);
		itemdetail.setName("ملفات الموقع");
		itemdetail.setItemDescription("files");
		results.add(itemdetail);

		itemdetail = new ItemDetails();
		itemdetail.setImageNumber(7);
		itemdetail.setName("اصدارتنا");
		itemdetail.setItemDescription("versions");
		results.add(itemdetail);

		itemdetail = new ItemDetails();
		itemdetail.setImageNumber(8);
		itemdetail.setName("موسوعة الحسبه");
		itemdetail.setItemDescription("encyclopedia");
		results.add(itemdetail);

		itemdetail = new ItemDetails();
		itemdetail.setImageNumber(9);
		itemdetail.setName("عن التطبيق");
		itemdetail.setItemDescription("about");
		results.add(itemdetail);

		return results;

	}

	/*
	 * public void startDownlod(int no, String curDate) { if
	 * (!isNetworkAvailable()) {
	 * 
	 * // check what section is to get url?'
	 * System.out.println("Staaaaaaaaaaatus " + isNetworkAvailable()); if (no ==
	 * 1) {
	 * 
	 * TABLE = "moshrfData"; createTable(TABLE); sec_id = 9995; String urlmoshrf
	 * =
	 * "http://almohtasb.com/api/json.ashx?section_no=9995&from_date=2000-06-12&to_date="
	 * + curDate + "&count=30"; getDataFromJason(urlmoshrf); //
	 * downloads.setText(" جارى التحميل  (10%)");
	 * 
	 * } else if (no == 2) {
	 * 
	 * TABLE = "newsData"; createTable(TABLE); sec_id = 6495; String urlnews =
	 * "http://almohtasb.com/api/json.ashx?section_no=6495&from_date=2000-06-12&to_date="
	 * + curDate + "&count=30";
	 * 
	 * getDataFromJason(urlnews); // downloads.setText(" جارى التحميل  (20%)");
	 * } else if (no == 3) { TABLE = "artData";
	 * 
	 * createTable(TABLE); sec_id = 26; String urlarticles =
	 * "http://almohtasb.com/api/json.ashx?section_no=26&from_date=2000-06-12&to_date="
	 * + curDate + "&count=30";
	 * 
	 * getDataFromJason(urlarticles); //
	 * downloads.setText(" جارى التحميل  (50%)"); } else if (no == 4) {
	 * 
	 * TABLE = "dialogsData"; createTable(TABLE); sec_id = 53; String urldialogs
	 * =
	 * "http://almohtasb.com/api/json.ashx?section_no=53&from_date=2000-06-12&to_date="
	 * + curDate + "&count=30";
	 * 
	 * getDataFromJason(urldialogs); //
	 * downloads.setText(" جارى التحميل  (60%)"); } else if (no == 5) {
	 * 
	 * TABLE = "libData"; sec_id = 23; createTable(TABLE); String urllib =
	 * "http://almohtasb.com/api/json.ashx?section_no=23&from_date=2000-11-12&to_date="
	 * + curDate + "&count=30";
	 * 
	 * getDataFromJason(urllib); // downloads.setText(" جارى التحميل  (70%)"); }
	 * else if (no == 6) {
	 * 
	 * TABLE = "meansData"; sec_id = 15993; createTable(TABLE); String
	 * urlmathmeans =
	 * "http://almohtasb.com/api/json.ashx?section_no=15993&from_date=2000-06-12&to_date="
	 * + curDate + "&count=40";
	 * 
	 * getDataFromJason(urlmathmeans); //
	 * downloads.setText(" جارى التحميل  (80%)"); } else if (no == 7) {
	 * 
	 * TABLE = "bookData"; sec_id = 15992; createTable(TABLE); String
	 * urlbookmeans =
	 * "http://almohtasb.com/api/json.ashx?section_no=15992&from_date=2000-06-12&to_date="
	 * + curDate + "&count=30";
	 * 
	 * getDataFromJason(urlbookmeans); //
	 * downloads.setText(" جارى التحميل  (95%)"); } else if (no == 8) { TABLE =
	 * "file-url"; sec_id = fileUrl; createTable(TABLE); String urlbookmeans =
	 * "http://almohtasb.com/api/json.ashx?section_no=‫‪12111&from_date=2000-06-12&to_date="
	 * + curDate + "&count=30";
	 * 
	 * getDataFromJason(urlbookmeans); //
	 * downloads.setText(" جارى التحميل  (95%)"); }
	 * 
	 * } else {
	 * 
	 * // // check what section is to get url? if (no == 1) { TABLE =
	 * "moshrfData"; try { db1 = openOrCreateDatabase(DBNAME,
	 * Context.MODE_PRIVATE, null); Cursor cursor =
	 * db1.rawQuery("SELECT * FROM  " + TABLE, null); if (cursor != null) { if
	 * (cursor.moveToFirst() && cursor.getCount() > 0) {
	 * 
	 * do { // whole data of column is fetched by // getColumnIndex() String
	 * TITLE = cursor.getString(cursor .getColumnIndex("TITLE")); String DATE =
	 * cursor.getString(cursor .getColumnIndex("DATE")); String TEXT =
	 * cursor.getString(cursor .getColumnIndex("TEXT"));
	 * System.out.println("Title \n" + TITLE);
	 * 
	 * } while (cursor.moveToNext()); } } } catch (SQLiteException e) { if
	 * (e.getMessage().toString().contains("no such table")) { Log.e("ERROR",
	 * "Creating table " + TABLE + "because it doesn't exist!");
	 * Toast.makeText(MainList.this, "لا توجد معلومات و قم بالاتصال بالانترنت",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } } finally { db1.close(); } } else if (no == 2) {
	 * 
	 * TABLE = "newsData"; try { db1 = openOrCreateDatabase(DBNAME,
	 * Context.MODE_PRIVATE, null); Cursor cursor =
	 * db1.rawQuery("SELECT * FROM  " + TABLE, null); if (cursor != null) { if
	 * (cursor.moveToFirst() && cursor.getCount() > 0) {
	 * 
	 * do { // whole data of column is fetched by // getColumnIndex() String
	 * TITLE = cursor.getString(cursor .getColumnIndex("TITLE")); String DATE =
	 * cursor.getString(cursor .getColumnIndex("DATE")); String TEXT =
	 * cursor.getString(cursor .getColumnIndex("TEXT"));
	 * System.out.println("Title \n" + TITLE);
	 * 
	 * } while (cursor.moveToNext()); } } } catch (SQLiteException e) { if
	 * (e.getMessage().toString().contains("no such table")) { Log.e("ERROR",
	 * "Creating table " + TABLE + "because it doesn't exist!");
	 * Toast.makeText(MainList.this, "لا توجد معلومات و قم بالاتصال بالانترنت",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } } finally { db1.close(); } //
	 * downloads.setText(" جارى التحميل  (20%)"); } else if (no == 3) { TABLE =
	 * "artData";
	 * 
	 * try { db1 = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
	 * Cursor cursor = db1.rawQuery("SELECT * FROM  " + TABLE, null); if (cursor
	 * != null) { if (cursor.moveToFirst() && cursor.getCount() > 0) {
	 * 
	 * do { // whole data of column is fetched by // getColumnIndex() String
	 * TITLE = cursor.getString(cursor .getColumnIndex("TITLE")); String DATE =
	 * cursor.getString(cursor .getColumnIndex("DATE")); String TEXT =
	 * cursor.getString(cursor .getColumnIndex("TEXT"));
	 * System.out.println("Title \n" + TITLE);
	 * 
	 * } while (cursor.moveToNext()); } } } catch (SQLiteException e) { if
	 * (e.getMessage().toString().contains("no such table")) { Log.e("ERROR",
	 * "Creating table " + TABLE + "because it doesn't exist!");
	 * Toast.makeText(MainList.this, "لا توجد معلومات و قم بالاتصال بالانترنت",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } } finally { db1.close(); } //
	 * downloads.setText(" جارى التحميل  (50%)"); } else if (no == 4) {
	 * 
	 * TABLE = "dialogsData"; try { db1 = openOrCreateDatabase(DBNAME,
	 * Context.MODE_PRIVATE, null); Cursor cursor =
	 * db1.rawQuery("SELECT * FROM  " + TABLE, null); if (cursor != null) { if
	 * (cursor.moveToFirst() && cursor.getCount() > 0) {
	 * 
	 * do { // whole data of column is fetched by // getColumnIndex() String
	 * TITLE = cursor.getString(cursor .getColumnIndex("TITLE")); String DATE =
	 * cursor.getString(cursor .getColumnIndex("DATE")); String TEXT =
	 * cursor.getString(cursor .getColumnIndex("TEXT"));
	 * System.out.println("Title \n" + TITLE);
	 * 
	 * } while (cursor.moveToNext()); } } } catch (SQLiteException e) { if
	 * (e.getMessage().toString().contains("no such table")) { Log.e("ERROR",
	 * "Creating table " + TABLE + "because it doesn't exist!");
	 * Toast.makeText(MainList.this, "لا توجد معلومات و قم بالاتصال بالانترنت",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } } finally { db1.close(); } } else if (no == 5) {
	 * 
	 * TABLE = "libData"; try { db1 = openOrCreateDatabase(DBNAME,
	 * Context.MODE_PRIVATE, null); Cursor cursor =
	 * db1.rawQuery("SELECT * FROM  " + TABLE, null); if (cursor != null) { if
	 * (cursor.moveToFirst() && cursor.getCount() > 0) {
	 * 
	 * do { // whole data of column is fetched by // getColumnIndex() String
	 * TITLE = cursor.getString(cursor .getColumnIndex("TITLE")); String DATE =
	 * cursor.getString(cursor .getColumnIndex("DATE")); String TEXT =
	 * cursor.getString(cursor .getColumnIndex("TEXT"));
	 * System.out.println("Title \n" + TITLE);
	 * 
	 * } while (cursor.moveToNext()); } } } catch (SQLiteException e) { if
	 * (e.getMessage().toString().contains("no such table")) { Log.e("ERROR",
	 * "Creating table " + TABLE + "because it doesn't exist!");
	 * Toast.makeText(MainList.this, "لا توجد معلومات و قم بالاتصال بالانترنت",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } } finally { db1.close(); } //
	 * downloads.setText(" جارى التحميل  (70%)"); } else if (no == 6) {
	 * 
	 * TABLE = "meansData"; try { db1 = openOrCreateDatabase(DBNAME,
	 * Context.MODE_PRIVATE, null); Cursor cursor =
	 * db1.rawQuery("SELECT * FROM  " + TABLE, null); if (cursor != null) { if
	 * (cursor.moveToFirst() && cursor.getCount() > 0) {
	 * 
	 * do { // whole data of column is fetched by // getColumnIndex() String
	 * TITLE = cursor.getString(cursor .getColumnIndex("TITLE")); String DATE =
	 * cursor.getString(cursor .getColumnIndex("DATE")); String TEXT =
	 * cursor.getString(cursor .getColumnIndex("TEXT"));
	 * System.out.println("Title \n" + TITLE);
	 * 
	 * } while (cursor.moveToNext()); } } } catch (SQLiteException e) { if
	 * (e.getMessage().toString().contains("no such table")) { Log.e("ERROR",
	 * "Creating table " + TABLE + "because it doesn't exist!");
	 * Toast.makeText(MainList.this, "لا توجد معلومات و قم بالاتصال بالانترنت",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } } finally { db1.close(); } //
	 * downloads.setText(" جارى التحميل  (80%)"); } else if (no == 7) {
	 * 
	 * TABLE = "bookData"; try { db1 = openOrCreateDatabase(DBNAME,
	 * Context.MODE_PRIVATE, null); Cursor cursor =
	 * db1.rawQuery("SELECT * FROM  " + TABLE, null); if (cursor != null) { if
	 * (cursor.moveToFirst() && cursor.getCount() > 0) {
	 * 
	 * do { // whole data of column is fetched by // getColumnIndex() String
	 * TITLE = cursor.getString(cursor .getColumnIndex("TITLE")); String DATE =
	 * cursor.getString(cursor .getColumnIndex("DATE")); String TEXT =
	 * cursor.getString(cursor .getColumnIndex("TEXT"));
	 * System.out.println("Title \n" + TITLE);
	 * 
	 * } while (cursor.moveToNext()); } } } catch (SQLiteException e) { if
	 * (e.getMessage().toString().contains("no such table")) { Log.e("ERROR",
	 * "Creating table " + TABLE + "because it doesn't exist!");
	 * Toast.makeText(MainList.this, "لا توجد معلومات و قم بالاتصال بالانترنت",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } } finally { db1.close(); } //
	 * downloads.setText(" جارى التحميل  (95%)"); } else if (no == 8) { TABLE =
	 * "file-url"; try { db1 = openOrCreateDatabase(DBNAME,
	 * Context.MODE_PRIVATE, null); Cursor cursor =
	 * db1.rawQuery("SELECT * FROM  " + TABLE, null); if (cursor != null) { if
	 * (cursor.moveToFirst() && cursor.getCount() > 0) {
	 * 
	 * do { // whole data of column is fetched by // getColumnIndex() String
	 * TITLE = cursor.getString(cursor .getColumnIndex("TITLE")); String DATE =
	 * cursor.getString(cursor .getColumnIndex("DATE")); String TEXT =
	 * cursor.getString(cursor .getColumnIndex("TEXT"));
	 * System.out.println("Title \n" + TITLE);
	 * 
	 * } while (cursor.moveToNext()); } } } catch (SQLiteException e) { if
	 * (e.getMessage().toString().contains("no such table")) { Log.e("ERROR",
	 * "Creating table " + TABLE + "because it doesn't exist!");
	 * Toast.makeText(MainList.this, "لا توجد معلومات و قم بالاتصال بالانترنت",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } } finally { db1.close(); } //
	 * downloads.setText(" جارى التحميل  (95%)"); } // Handler han = new
	 * Handler(); // han.postDelayed(new Runnable() { // // @Override // public
	 * void run() { // // goNext(); // // } // }, 2000);
	 * 
	 * }
	 * 
	 * }
	 */
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
					+ "(ID INTEGER PRIMARY KEY,SectionID INTEGER ,ElEMENTID INTEGER, TITLE VARCHAR ,DATE VARCHAR,TEXT VARCHAR ,file_url VARCHAR); ");
		} catch (Exception e) {
			// Toast.makeText(context, "Error in creating table",
			// Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} finally {
			db1.close();
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
			// startDownlod(no, formattedDate);

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
				if (sb.equals("لا توجد مواد في هذا القسم")) {

				} else {
					json = sb.toString();
					System.out.println(json);

					// try parse the string to a JSON object
					try {
						jObj = new JSONObject(json);
					} catch (JSONException e) {
						Log.e("JSON Parser",
								"Error parsing data " + e.toString());
						db1.close();

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
								String fileUrl = c.getString(TAG_URL);

								System.out.println("Title + \n" + title);
								try {
									db1 = openOrCreateDatabase(DBNAME,
											Context.MODE_PRIVATE, null);
									db1.execSQL("INSERT INTO "
											+ TABLE
											+ "(SectionID ,ElEMENTID ,TITLE ,DATE,TEXT,file_url) VALUES ("
											+ moshrfid + " , " + id + " , '"
											+ title + "', '" + date + "' , '"
											+ text + "','" + fileUrl + "')");
								} catch (SQLException e) {
									// TODO: handle exception
									e.printStackTrace();
								} finally {
									db1.close();
								}
								try {

									db1 = openOrCreateDatabase(DBNAME,
											Context.MODE_PRIVATE, null);
									Cursor cursor = db1.rawQuery(
											"SELECT * FROM " + TABLE, null);
									if (cursor != null) {
										if (cursor.moveToFirst()) {
											do {
												// whole data of column is
												// fetched
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
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								} finally {
									db1.close();
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
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
