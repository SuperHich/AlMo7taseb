package com.elshawaf.freelance.elmo7tsp;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.elshawaf.freelance.elmo7tsp.jason.JSONParser;

public class ListOfTitles extends Activity {
	ImageView image;
	TextView title;
	LinearLayout layout1, layout2;
	int imgres;
	String txtTitle;
	ListView listoftitles;
	ArrayList<String> titles = new ArrayList<String>();
	boolean available = true;
	boolean updateable ;
	static String formattedDate;
	Context context;
	// _______________Download variables_________________________
	private Handler handler = new Handler(Looper.getMainLooper());
	private static String urlmoshrf = "http://almohtasb.com/api/json.ashx?section_no=16929&from_date=2000-06-12&to_date={{date}}&count=40";
	private static String urlarticles = "http://almohtasb.com/api/json.ashx?section_no=16751&from_date=2000-06-12&to_date={{date}}&count=40";
	private static String urlversions = "http://almohtasb.com/api/json.ashx?section_no=8499&from_date=2000-06-12&to_date={{date}}&count=40";
	private static String urlmathmeans = "http://almohtasb.com/api/json.ashx?section_no=15993&from_date=2000-06-12&to_date={{date}}&count=40";
	private static String urlbookmeans = "http://almohtasb.com/api/json.ashx?section_no=15992&from_date=2000-06-12&to_date={{date}}&count=40";
	private static String urldialogs = "http://almohtasb.com/api/json.ashx?section_no=16753&from_date=2000-06-12&to_date={{date}}&count=40";
	private static String urllib = "http://almohtasb.com/api/json.ashx?section_no=16752&from_date=2000-06-12&to_date={{date}}&count=40";
	private static String urlnews = "http://almohtasb.com/api/json.ashx?section_no=16728&from_date=2000-06-12&to_date={{date}}&count=40";
	private static String urlfile = "http://almohtasb.com/api/json.ashx?section_no={{number}}&from_date=2000-06-12&to_date={{date}}&count=40";

	private static String[] urls = { urlmoshrf, urlarticles, urlversions,
			urlmathmeans, urlbookmeans, urldialogs, urllib, urlfile, urlnews };
	private static int moshrfid = 16929;
	private static int articlesid = 16751;
	private static int versionsid = 8499;
	private static int mathmeansid = 15993;
	private static int bookmeansid = 15992;
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
	SQLiteDatabase db1 = null;
	String TABLE = "Data";
	private static String DBNAME = "Elmohtsp3.db";
	TitlesAdapter adapter;
	public JSONObject jObj = null;
	public ProgressDialog dialog;
	// alldata JSONArray
	JSONArray alldata = null;
	boolean flag = true;
	ImageButton facebook, twitter, youtube, home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(ListOfTitles.this, "e35c2934");
		setContentView(R.layout.display_answers);

		formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		image = (ImageView) findViewById(R.id.image);
		title = (TextView) findViewById(R.id.text);
		listoftitles = (ListView) findViewById(R.id.lv);
		adapter = new TitlesAdapter(this, titles);
		listoftitles.setAdapter(adapter);
		facebook = (ImageButton) findViewById(R.id.fb);
		twitter = (ImageButton) findViewById(R.id.twitter);
		youtube = (ImageButton) findViewById(R.id.yt);
		context = this;

        available = haveNetworkConnection();
       // isNetworkAvailable = haveNetworkConnection();

		home = (ImageButton) findViewById(R.id.menu);

		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ListOfTitles.this, MainList.class);
				intent.putExtra("isFirstVisit", false);
				startActivity(intent);
				finish();
			}
		});

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

		final Bundle bundle = getIntent() .getExtras();
		imgres = bundle.getInt("img");
		txtTitle = bundle.getString("txt");
        updateable =bundle.getBoolean("update");

		image.setImageResource(imgres);
		title.setText(txtTitle);

		dialog = new ProgressDialog(context);
		dialog.setMessage("الرجاء الإنتظار ...");
		dialog.show();

		final Handler handler = new Handler();

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

//				if (available && updateable) {
				if (available) {

					System.out.println("isNetworkAvailable() == true");
					available = true;
					// check what section is to get url?

					if (txtTitle.equals("ركن المشرف")) {
						TABLE = "moshrfData";
						createTable(TABLE);
						sec_id = moshrfid;
						urlmoshrf = urlmoshrf
								.replace("{{date}}", formattedDate);
						// System.out.println("mosref url is : " + urlmoshrf);

						getDataFromJason(urlmoshrf);

					} else if (txtTitle.equals("الاخبار")) {
						TABLE = "newsData";
						createTable(TABLE);
						sec_id = newsid;
						urlnews = urlnews.replace("{{date}}", formattedDate);
						getDataFromJason(urlnews);

					} else if (txtTitle.equals("المقالات")) {
						TABLE = "artData";
						createTable(TABLE);
						sec_id = articlesid;
						urlarticles = urlarticles.replace("{{date}}",
								formattedDate);
						// System.out.println("iam out and first");
						getDataFromJason(urlarticles);
						// System.out.println("iam out and end");
					} else if (txtTitle.equals("الحوارات")) {
						TABLE = "dialogsData";
						createTable(TABLE);
						sec_id = urldialogsid;
						urldialogs = urldialogs.replace("{{date}}",
								formattedDate);
						getDataFromJason(urldialogs);

					} else if (txtTitle.equals("مكتبة المحتسب")) {
						TABLE = "libData";
						sec_id = urllibid;
						createTable(TABLE);
						urllib = urllib.replace("{{date}}", formattedDate);
						getDataFromJason(urllib);

					} else if (txtTitle.equals("ملفات الموقع")) {
						//TABLE = "filesData";
						//createTable(TABLE);
						//urlfile = urlfile.replace("{{date}}", formattedDate);
						//getDataFromJason(urlfile);

						int verionchooice = bundle.getInt("vsd");
						if (verionchooice == 1) {

							TABLE = "num1";
							sec_id = 7502;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

						} else if (verionchooice == 2) {
                            sec_id = 15626;
							TABLE = "num2";
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 3) {
							TABLE = "num3";
							sec_id = 10010;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 4) {
							TABLE = "num4";
							sec_id = 8979;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 5) {

							TABLE = "num5";
							sec_id = 11897;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 6) {
							TABLE = "num6";
							sec_id = 10341;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 7) {
							TABLE = "num7";
							sec_id = 11151;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 8) {
							TABLE = "num8";
							sec_id = 13650;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 9) {
							TABLE = "num9";
							sec_id = 12230;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);


                        } else if (verionchooice == 10) {
							TABLE = "num10";
							sec_id = 16280;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 11) {
							TABLE = "num11";
							sec_id = 12863;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 12) {
							TABLE = "num12";
							sec_id = 14217;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 13) {
							TABLE = "num13";
							sec_id = 10103;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 14) {
							TABLE = "num14";
							sec_id = 10266;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 15) {
							TABLE = "num15";
							sec_id = 14563;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 16) {
							TABLE = "num16";
							sec_id = 11636;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 17) {
							TABLE = "num17";
							sec_id = 13106;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 18) {
							TABLE = "num18";
							sec_id = 12622;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        } else if (verionchooice == 19) {
							TABLE = "num19";
							sec_id = 16428;
                            createTable(TABLE);
                            String currentURL = urlfile;
                            currentURL = currentURL
                                    .replace("{{date}}", formattedDate);
                            currentURL = currentURL
                                    .replace("{{number}}", "" + sec_id);
                            System.out.println("the current url : " + currentURL);
                            getDataFromJason(currentURL);

                        }

					} else if (txtTitle.equals("اصدارتنا")) {

						String verionchooice = bundle.getString("vsd");
						if (verionchooice.equals("وسائل احتسابية")) {
							TABLE = "meansData";
							sec_id = mathmeansid;
							createTable(TABLE);
							urlmathmeans = urlmathmeans.replace("{{date}}",
									formattedDate);
							getDataFromJason(urlmathmeans);
						} else if (verionchooice.equals("كتب احتسابية")) {
							TABLE = "bookData";
							sec_id = bookmeansid;
							createTable(TABLE);
							urlbookmeans = urlbookmeans.replace("{{date}}",
									formattedDate);
							getDataFromJason(urlbookmeans);
						}

					}
				} else {
					System.out.println("isNetworkAvailable() == false");
					// check what section is to get url?
					if (txtTitle.equals("ركن المشرف")) {

						TABLE = "moshrfData";
						try {
							db1 = openOrCreateDatabase(DBNAME,
									Context.MODE_PRIVATE, null);
							Cursor cursor = db1.rawQuery("SELECT * FROM  "
									+ TABLE, null);
							if (cursor != null) {
								if (cursor.moveToFirst()
										&& cursor.getCount() > 0) {

									do {
										// whole data of column is fetched by
										// getColumnIndex()
										String TITLE = cursor.getString(cursor
												.getColumnIndex("TITLE"));
										String DATE = cursor.getString(cursor
												.getColumnIndex("DATE"));
										String TEXT = cursor.getString(cursor
												.getColumnIndex("TEXT"));
										// System.out.println("Title \n" +
										// TITLE);

										titles.add(TITLE);

										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												updateadapter();
											}
										});

									} while (cursor.moveToNext());
								}
							}
						} catch (SQLiteException e) {
							if (e.getMessage().toString()
									.contains("no such table")) {
								Log.e("ERROR", "Creating table " + TABLE
										+ "because it doesn't exist!");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ListOfTitles.this,
                                                "لا توجد معلومات و قم بالاتصال بالانترنت",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

							}
						}

						db1.close();

					} else if (txtTitle.equals("الاخبار")) {
						TABLE = "newsData";
						try {
							db1 = openOrCreateDatabase(DBNAME,
									Context.MODE_PRIVATE, null);
							Cursor cursor = db1.rawQuery("SELECT * FROM   "
									+ TABLE, null);
							if (cursor != null) {
								if (cursor.moveToFirst()
										&& cursor.getCount() > 0) {

									do {
										// whole data of column is fetched by
										// getColumnIndex()
										String TITLE = cursor.getString(cursor
												.getColumnIndex("TITLE"));
										String DATE = cursor.getString(cursor
												.getColumnIndex("DATE"));
										String TEXT = cursor.getString(cursor
												.getColumnIndex("TEXT"));
										// System.out.println("Title \n" +
										// TITLE);

										titles.add(TITLE);

										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												updateadapter();
											}
										});

									} while (cursor.moveToNext());
								}
							}
						} catch (SQLiteException e) {
							if (e.getMessage().toString()
									.contains("no such table")) {
								Log.e("ERROR", "Creating table " + TABLE
										+ "because it doesn't exist!");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ListOfTitles.this,
                                                "لا توجد معلومات و قم بالاتصال بالانترنت",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

							}

						}
						db1.close();

					} else if (txtTitle.equals("المقالات")) {
						TABLE = "artData";
						try {
							db1 = openOrCreateDatabase(DBNAME,
									Context.MODE_PRIVATE, null);
							Cursor cursor = db1.rawQuery("SELECT * FROM  "
									+ TABLE, null);
							if (cursor != null) {
								if (cursor.moveToFirst()
										&& cursor.getCount() > 0) {

									do {
										// whole data of column is fetched by
										// getColumnIndex()
										String TITLE = cursor.getString(cursor
												.getColumnIndex("TITLE"));
										String DATE = cursor.getString(cursor
												.getColumnIndex("DATE"));
										String TEXT = cursor.getString(cursor
												.getColumnIndex("TEXT"));
										// System.out.println("Title \n" +
										// TITLE);

										titles.add(TITLE);

										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												updateadapter();
											}
										});

									} while (cursor.moveToNext());
								}
							}
						} catch (SQLiteException e) {
							if (e.getMessage().toString()
									.contains("no such table")) {
								Log.e("ERROR", "Creating table " + TABLE
										+ "because it doesn't exist!");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ListOfTitles.this,
                                                "لا توجد معلومات و قم بالاتصال بالانترنت",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

							}
						}
						db1.close();

					} else if (txtTitle.equals("الحوارات")) {
						TABLE = "dialogsData";
						try {
							db1 = openOrCreateDatabase(DBNAME,
									Context.MODE_PRIVATE, null);
							Cursor cursor = db1.rawQuery("SELECT * FROM  "
									+ TABLE, null);
							if (cursor != null) {
								if (cursor.moveToFirst()
										&& cursor.getCount() > 0) {

									do {
										// whole data of column is fetched by
										// getColumnIndex()
										String TITLE = cursor.getString(cursor
												.getColumnIndex("TITLE"));
										String DATE = cursor.getString(cursor
												.getColumnIndex("DATE"));
										String TEXT = cursor.getString(cursor
												.getColumnIndex("TEXT"));
										// System.out.println("Title \n" +
										// TITLE);

										titles.add(TITLE);

										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												updateadapter();
											}
										});

									} while (cursor.moveToNext());
								}
							}
						} catch (SQLiteException e) {
							if (e.getMessage().toString()
									.contains("no such table")) {
								Log.e("ERROR", "Creating table " + TABLE
										+ "because it doesn't exist!");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ListOfTitles.this,
                                                "لا توجد معلومات و قم بالاتصال بالانترنت",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

							}
						}
						db1.close();

					} else if (txtTitle.equals("مكتبة المحتسب")) {
						TABLE = "libData";
						try {
							db1 = openOrCreateDatabase(DBNAME,
									Context.MODE_PRIVATE, null);
							Cursor cursor = db1.rawQuery("SELECT * FROM  "
									+ TABLE, null);
							if (cursor != null) {
								if (cursor.moveToFirst()
										&& cursor.getCount() > 0) {

									do {
										// whole data of column is fetched by
										// getColumnIndex()
										String TITLE = cursor.getString(cursor
												.getColumnIndex("TITLE"));
										String DATE = cursor.getString(cursor
												.getColumnIndex("DATE"));
										String TEXT = cursor.getString(cursor
												.getColumnIndex("TEXT"));
										// System.out.println("Title \n" +
										// TITLE);

										titles.add(TITLE);

										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												updateadapter();
											}
										});

									} while (cursor.moveToNext());
								}
							}
						} catch (SQLiteException e) {
							if (e.getMessage().toString()
									.contains("no such table")) {
								available = false;
								Log.e("ERROR", "Creating table " + TABLE
										+ "because it doesn't exist!");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ListOfTitles.this,
                                                "لا توجد معلومات و قم بالاتصال بالانترنت",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });


							}

						}

						finally {
							db1.close();
						}
					} else if (txtTitle.equals("ملفات الموقع")) {
						//TABLE = "filesData";

                        int verionchooice = bundle.getInt("vsd");
                        if (verionchooice == 1) {
                            TABLE = "num1";
                            sec_id = 7502;

                        } else if (verionchooice == 2) {
                            TABLE = "num2";
                            sec_id = 15626;

                        } else if (verionchooice == 3) {
                            TABLE = "num3";
                            sec_id = 10010;

                        } else if (verionchooice == 4) {
                            TABLE = "num4";
                            sec_id = 8979;

                        } else if (verionchooice == 5) {
                            TABLE = "num5";
                            sec_id = 11897;

                        } else if (verionchooice == 6) {
                            TABLE = "num6";
                            sec_id = 10341;

                        } else if (verionchooice == 7) {
                            TABLE = "num7";
                            sec_id = 11151;

                        } else if (verionchooice == 8) {
                            TABLE = "num8";
                            sec_id = 13650;

                        } else if (verionchooice == 9) {
                            TABLE = "num9";
                            sec_id = 12230;

                        } else if (verionchooice == 10) {
                            TABLE = "num10";
                            sec_id = 16280;

                        } else if (verionchooice == 11) {
                            TABLE = "num11";
                            sec_id = 12863;

                        } else if (verionchooice == 12) {
                            TABLE = "num12";
                            sec_id = 14217;

                        } else if (verionchooice == 13) {
                            TABLE = "num13";
                            sec_id = 10103;

                        } else if (verionchooice == 14) {
                            TABLE = "num14";
                            sec_id = 10266;

                        } else if (verionchooice == 15) {
                            TABLE = "num15";
                            sec_id = 14563;

                        } else if (verionchooice == 16) {
                            TABLE = "num16";
                            sec_id = 11636;

                        } else if (verionchooice == 17) {
                            TABLE = "num17";
                            sec_id = 13106;

                        } else if (verionchooice == 18) {
                            TABLE = "num18";
                            sec_id = 12622;

                        } else if (verionchooice == 19) {
                            TABLE = "num19";
                            sec_id = 16428;

                        }


						try {
							db1 = openOrCreateDatabase(DBNAME,
									Context.MODE_PRIVATE, null);
							Cursor cursor = db1.rawQuery("SELECT * FROM  "
									+ TABLE, null);
							if (cursor != null) {
								if (cursor.moveToFirst()
										&& cursor.getCount() > 0) {

									do {
										// whole data of column is fetched by
										// getColumnIndex()
										String TITLE = cursor.getString(cursor
												.getColumnIndex("TITLE"));
										String DATE = cursor.getString(cursor
												.getColumnIndex("DATE"));
										String TEXT = cursor.getString(cursor
												.getColumnIndex("TEXT"));
										// System.out.println("Title \n" +
										// TITLE);

										titles.add(TITLE);

										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												updateadapter();
											}
										});

									} while (cursor.moveToNext());
								}
							}
						} catch (SQLiteException e) {
							if (e.getMessage().toString()
									.contains("no such table")) {
								available = false;
								Log.e("ERROR", "Creating table " + TABLE
										+ "because it doesn't exist!");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ListOfTitles.this,
                                                "لا توجد معلومات و قم بالاتصال بالانترنت",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

							}

						}

						finally {
							db1.close();
						}
					} else if (txtTitle.equals("اصدارتنا")) {

						if (bundle.get("vsd").equals("وسائل احتسابية")) {

							TABLE = "meansData";
							try {
								db1 = openOrCreateDatabase(DBNAME,
										Context.MODE_PRIVATE, null);
								Cursor cursor = db1.rawQuery("SELECT * FROM  "
										+ TABLE, null);
								if (cursor != null) {
									if (cursor.moveToFirst()
											&& cursor.getCount() > 0) {

										do {
											// whole data of column is fetched
											// by
											// getColumnIndex()
											String TITLE = cursor.getString(cursor
													.getColumnIndex("TITLE"));
											String DATE = cursor.getString(cursor
													.getColumnIndex("DATE"));
											String TEXT = cursor.getString(cursor
													.getColumnIndex("TEXT"));
											// System.out.println("Title \n" +
											// TITLE);

											titles.add(TITLE);

											runOnUiThread(new Runnable() {
												@Override
												public void run() {
													updateadapter();
												}
											});

										} while (cursor.moveToNext());
									}
								}
							} catch (SQLiteException e) {
								if (e.getMessage().toString()
										.contains("no such table")) {
									available = false;
									Log.e("ERROR", "Creating table " + TABLE
											+ "because it doesn't exist!");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ListOfTitles.this,
                                                    "لا توجد معلومات و قم بالاتصال بالانترنت",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });

								}

							}

							finally {
								db1.close();
							}

						} else {

							TABLE = "bookData";
							try {
								db1 = openOrCreateDatabase(DBNAME,
										Context.MODE_PRIVATE, null);
								Cursor cursor = db1.rawQuery("SELECT * FROM  "
										+ TABLE, null);
								if (cursor != null) {
									if (cursor.moveToFirst()
											&& cursor.getCount() > 0) {

										do {
											// whole data of column is fetched
											// by
											// getColumnIndex()
											String TITLE = cursor.getString(cursor
													.getColumnIndex("TITLE"));
											String DATE = cursor.getString(cursor
													.getColumnIndex("DATE"));
											String TEXT = cursor.getString(cursor
													.getColumnIndex("TEXT"));
											// System.out.println("Title \n" +
											// TITLE);

											titles.add(TITLE);

											runOnUiThread(new Runnable() {
												@Override
												public void run() {
													updateadapter();
												}
											});

										} while (cursor.moveToNext());
									}
								}
							} catch (SQLiteException e) {
								if (e.getMessage().toString()
										.contains("no such table")) {
									available = false;
									Log.e("ERROR", "Creating table " + TABLE
											+ "because it doesn't exist!");

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ListOfTitles.this,
                                                    "لا توجد معلومات و قم بالاتصال بالانترنت",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });

								}

							}

							finally {
								db1.close();
							}

						}

					}

				}

				if (dialog != null) {
					dialog.dismiss();
				}

			}

		}, 1500);

		listoftitles.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {

				String selectedtitle = ((TextView) view
						.findViewById(R.id.titletv)).getText().toString();
				int section_id = sec_id;
				System.out.println("Section Id is : " + section_id);
				Intent intent = new Intent(ListOfTitles.this,
						TitleDetails.class);
				intent.putExtra("img", imgres);
				intent.putExtra("txt", txtTitle);
				intent.putExtra("secid", section_id);
				intent.putExtra("title", selectedtitle);
				intent.putExtra("table", TABLE);

				startActivity(intent);
					//finish();


				BugSenseHandler.closeSession(ListOfTitles.this);

			}
		});

	}

	public void onStart() {
		super.onStart();
		// tToast("onStart");
	}

	public void onRestart() {
		super.onRestart();
		// tToast("onRestart");
	}

	public void onResume() {
		super.onResume();
		// tToast("onResume");
	}

	public void onPause() {
		super.onPause();
		// tToast("onPause: bye bye!");
	}

	public void onStop() {
		super.onStop();
		// tToast("onStop.");
	}

	public void onDestroy() {
		super.onStop();
		// tToast("onDestroy.");
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	private void tToast(String s) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, s, duration);
		toast.show();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();

	}

	// private boolean isNetworkAvailable() {
	// ConnectivityManager connectivityManager = (ConnectivityManager) this
	// .getSystemService(Context.CONNECTIVITY_SERVICE);
	// NetworkInfo activeNetworkInfo = connectivityManager
	// .getActiveNetworkInfo();
	// return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	// }

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

	public void updateadapter() {

		// ((BaseAdapter) listoftitles.getAdapter()).notifyDataSetChanged();
		adapter.notifyDataSetChanged();

	}

	@SuppressLint("NewApi")
	public void getDataFromJason(String url) {

		final String theURL = url;
		// System.out.println("iam in");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// Creating JSON Parser instance

		// final JSONParse jParser = new JSONParse(this);

		final JSONParser jparser = new JSONParser(this);

		jObj = jparser.getJSONFromUrl(theURL);
        titles.clear();
		try {
			// Getting Array of alldata
			if (jObj != null) {

				System.out.println("insertion to database ");
				alldata = jObj.getJSONArray(TAG_MAIN);



				// looping through All alldata
				for (int i = 0; i < alldata.length(); i++) {
					JSONObject c = alldata.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_ID);
					String date = c.getString(TAG_DATE);
					String title = c.getString(TAG_TITLE);
					String text = c.getString(TAG_TEXT);
					// System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb : "+
					// text);
					// text = Html.fromHtml(text).toString();
					// text = text.replaceAll("\"","\\\"");
					// System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb : "+
					// text);
					// text = Html.escapeHtml(text);

					// Sending side
					byte[] data = null;
					try {
						data = text.getBytes("UTF-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					String base64 = Base64.encodeToString(data, Base64.DEFAULT);

					String fileUrl = c.getString(TAG_URL);

					// System.out.println("the text is : " + base64);
					titles.add(title);
					// System.out.println("Title + \n" + title);

					try {

						db1 = openOrCreateDatabase(DBNAME,
								Context.MODE_PRIVATE, null);

						db1.execSQL("INSERT INTO "
								+ TABLE
								+ "(SectionID ,ElEMENTID ,TITLE ,DATE,TEXT,file_url) VALUES ("
								+ moshrfid + " , " + id + " , '" + title
								+ "', '" + date + "' , '" + base64 + "','"
								+ fileUrl + "')");

					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
					} finally {
						db1.close();
					}

				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						updateadapter();
					}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class TitlesAdapter extends ArrayAdapter<String> {
		private ArrayList<String> values;
		LayoutInflater l_inflater;
		Context context;

		public TitlesAdapter(Context context, ArrayList<String> values) {
			super(context, R.layout.titles_listrow, values);
			this.values = values;
			this.context = context;
		}

		class ViewHolder {
			TextView titletv;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.titles_listrow, parent,
					false);

			TextView titletextview = (TextView) convertView
					.findViewById(R.id.titletv);

			titletextview.setText(values.get(position));
			return convertView;
		}

	}

	/*
	 * private class JSONParse extends AsyncTask<String, String, JSONObject> {
	 * 
	 * AsyncTask<String, String, JSONObject> a = this; Context context;
	 * InputStream is = null;
	 * 
	 * String json = ""; private String filenamemoshrf = "Jasondatamoshrf";
	 * private String filenamearticles = "Jasondataarticles"; private String
	 * filenameversions = "Jasondataversions"; private String filenamemathmeans
	 * = "Jasondatamathmeans"; private String filenamebookmeans =
	 * "Jasondatabookmeans"; private String filenamedialogs =
	 * "Jasondatadialogs"; private String filenamelib = "Jasondatalib"; private
	 * String filenamenews = "Jasondatanews"; private String[] names = {
	 * filenamemoshrf, filenamearticles, filenameversions, filenamemathmeans,
	 * filenamebookmeans, filenamedialogs, filenamelib, filenamenews }; private
	 * String filepath = "MyFileStorage";
	 * 
	 * public JSONParse(Context con) { // dir = new
	 * File(Environment.getExternalStorageDirectory(), // "Elmo7tsp"); //
	 * dir.mkdirs(); // myInternalFile = new //
	 * File(Environment.getExternalStorageDirectory() // .toString() +
	 * "/Elmo7tsp/" + filename + ".txt"); // try { //
	 * myInternalFile.createNewFile(); // } catch (IOException e1) { // // TODO
	 * Auto-generated catch block // e1.printStackTrace(); // } context = con; }
	 * 
	 * @Override protected void onPostExecute(JSONObject result) { // TODO
	 * Auto-generated method stub super.onPostExecute(result); //
	 * progress.dismiss(); if (jObj == null) { runOnUiThread(new Runnable() {
	 * 
	 * @Override public void run() { Toast.makeText(context,
	 * "لا توجد مواد في هذا القسم", Toast.LENGTH_LONG).show(); // finish(); }
	 * }); }
	 * 
	 * }
	 * 
	 * @Override protected JSONObject doInBackground(String... params) {
	 * 
	 * // Making HTTP request try { // defaultHttpClient DefaultHttpClient
	 * httpClient = new DefaultHttpClient(); HttpPost httpPost = new
	 * HttpPost(params[0]);
	 * 
	 * HttpResponse httpResponse = httpClient.execute(httpPost); HttpEntity
	 * httpEntity = httpResponse.getEntity(); is = httpEntity.getContent();
	 * 
	 * } catch (UnsupportedEncodingException e) { e.printStackTrace(); } catch
	 * (ClientProtocolException e) { e.printStackTrace(); } catch (IOException
	 * e) { e.printStackTrace(); }
	 * 
	 * try { BufferedReader reader = new BufferedReader( new
	 * InputStreamReader(is, "UTF-8"), 8); StringBuilder sb = new
	 * StringBuilder(); String line = null; while ((line = reader.readLine()) !=
	 * null) { sb.append(line + "\n");
	 * 
	 * } is.close(); json = sb.toString();
	 * 
	 * } catch (Exception e) { // Log.e("Buffer Error",
	 * "Error converting result " + // e.toString()); e.printStackTrace(); }
	 * 
	 * // try parse the string to a JSON object try { jObj = new
	 * JSONObject(json); } catch (JSONException e) { // Log.e("JSON Parser",
	 * "Error parsing data " + e.toString()); e.printStackTrace(); }
	 * 
	 * return jObj; }
	 * 
	 * @Override protected void onPreExecute() { // Show progress Dialog here
	 * super.onPreExecute();
	 * 
	 * } }
	 */
	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// your code
//			Intent intent = new Intent(ListOfTitles.this, MainList.class);
//			intent.putExtra("isFirstVisit", false);
//			startActivity(intent);
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
