package com.elshawaf.freelance.elmo7tsp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VersionsChoose extends Activity {

	ImageView image;
	TextView title;
	LinearLayout layout1, layout2;
	int imgres;
	String txtTitle;
	ImageButton facebook, twitter, youtube, home;
    boolean updateable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_choose);
		image = (ImageView) findViewById(R.id.image);
		title = (TextView) findViewById(R.id.text);
		layout1 = (LinearLayout) findViewById(R.id.secondlayout);
		layout2 = (LinearLayout) findViewById(R.id.thirdlayout);

		facebook = (ImageButton) findViewById(R.id.fb);
		twitter = (ImageButton) findViewById(R.id.twitter);
		youtube = (ImageButton) findViewById(R.id.yt);

		home = (ImageButton) findViewById(R.id.menu);

		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(VersionsChoose.this, MainList.class);
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
        updateable = bundle.getBoolean("update");

		image.setImageResource(imgres);
		title.setText(txtTitle);

		layout1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(VersionsChoose.this,
						ListOfTitles.class);
				intent.putExtra("txt", txtTitle);
				intent.putExtra("img", imgres);
				intent.putExtra("vsd", "وسائل احتسابية");
                intent.putExtra("update", updateable);
				startActivity(intent);
			}
		});
		layout2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(VersionsChoose.this,
						ListOfTitles.class);
				intent.putExtra("txt", txtTitle);
				intent.putExtra("img", imgres);
				intent.putExtra("vsd", "كتب احتسابية");
                intent.putExtra("update", updateable);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// your code
//			Intent intent = new Intent(VersionsChoose.this, MainList.class);
//			intent.putExtra("isFirstVisit", false);
//			startActivity(intent);
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
