package com.elshawaf.freelance.elmo7tsp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class TitleDetails extends Activity {

	ImageView image;
	TextView title;
	LinearLayout layout1, layout2;
	int imgres;
	String txtTitle;
	SQLiteDatabase db1 = null;
	String TABLE = "Data";
	int sectionID;
	String titlename;
	int elementID;
	String fileUrl;
	private static String DBNAME = "Elmohtsp3.db";
	TextView infoTv;
	WebView webView;
	String text;
	String text1 = null;
	ImageButton facebook, twitter, youtube, home;
	ImageButton facebookShare, twitterShare, fileView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title_details);

		image = (ImageView) findViewById(R.id.image);
		title = (TextView) findViewById(R.id.text);
		// infoTv=(TextView)findViewById(R.id.titinfo);
		// infoTv.setMovementMethod(new ScrollingMovementMethod());
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);

		facebook = (ImageButton) findViewById(R.id.fb);
		twitter = (ImageButton) findViewById(R.id.twitter);
		youtube = (ImageButton) findViewById(R.id.yt);

		facebookShare = (ImageButton) findViewById(R.id.fb_sahre);
		twitterShare = (ImageButton) findViewById(R.id.twitter_share);
		fileView = (ImageButton) findViewById(R.id.file_view);

		home = (ImageButton) findViewById(R.id.menu);

		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TitleDetails.this, MainList.class);
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
		Bundle bundle = getIntent().getExtras();
		imgres = bundle.getInt("img");
		txtTitle = bundle.getString("txt");
		TABLE = bundle.getString("table");
		sectionID = bundle.getInt("secid");
		titlename = bundle.getString("title");
		// System.out.println("Table = " + TABLE + "--------------Title =" +
		// titlename);
		image.setImageResource(imgres);
		title.setText(txtTitle);

		db1 = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
		String[] selection = new String[1];
		selection[0] = "%" + titlename + "%";
		Cursor cursor = db1.rawQuery("SELECT * FROM " + TABLE
				+ " WHERE  TITLE LIKE ?", selection);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					text = cursor.getString(cursor.getColumnIndex("TEXT"));

					// Receiving side
					byte[] data1 = Base64.decode(text, Base64.DEFAULT);

					try {
						text1 = new String(data1, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					text1 = text1.replace("src=\"",
							"src=\"http://almohtasb.com/");
					// System.out
					// .println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa : 88 : "
					// + text);

					// infoTv.setText(Html.fromHtml(TEXT));
					elementID = cursor.getInt(cursor
							.getColumnIndex("ElEMENTID"));
					fileUrl = cursor.getString(cursor
							.getColumnIndex("file_url"));

				} while (cursor.moveToNext());

			}

            String newText;

            if (text1.length() > 2){

                newText = "<!DOCTYPE html><head> <meta http-equiv=\"Content-Type\" "
                        + "content=\"text/html; charset=utf-8\">"
                        + "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>"
                        + text1 + "</body></html>";

                webView.loadData(newText, "text/html; charset=UTF-8", null);

            }else {

                newText = fileUrl;

                webView.getSettings().setJavaScriptEnabled(true);
                String url = "http://docs.google.com/viewer?embedded=true&url="+newText;
                webView.loadUrl(url);



            }



			if (fileUrl.length() > 5) {

                fileView.setVisibility(View.VISIBLE);

                fileView.setEnabled(true);
                fileView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent browserIntent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(fileUrl));
                        startActivity(browserIntent);
                    }
                });

			} else {

                fileView.setVisibility(View.GONE);


			}
			facebookShare.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            "http://www.almohtasb.com/main/articles.aspx?article_no="
                                    + elementID);
                    startActivity(Intent.createChooser(sharingIntent,
                            "Share using"));
                }
            });
			twitterShare.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					String tweetUrl = "https://twitter.com/intent/tweet?url=http://www.almohtasb.com/main/articles.aspx?article_no="
							+ elementID;
					Uri uri = Uri.parse(tweetUrl);
					startActivity(new Intent(Intent.ACTION_VIEW, uri));
				}
			});
		}
		db1.close();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// your code
//			System.out.println("sssssssssssssssssssssssssssssssssss");
//			Intent intent = new Intent(TitleDetails.this, ListOfTitles.class);
//			System.out.println("titlename : " + txtTitle);
//			System.out.println("imgres : " + imgres);
//			intent.putExtra("txt", txtTitle);
//			intent.putExtra("img", imgres);
//			startActivity(intent);
          //  moveTaskToBack(true);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}