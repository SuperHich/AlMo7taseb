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

public class SiteFilesActivity extends Activity {

	ImageView image;
	TextView title;
	LinearLayout layout2, layout3, layout4, layout5, layout6, layout7, layout8,
			layout9, layout10, layout11, layout12, layout13, layout14,
			layout15, layout16, layout17, layout18, layout19, layout20;
	int imgres;
	String txtTitle;
	ImageButton facebook, twitter, youtube, home;
    boolean updateable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.site_files_list);
		image = (ImageView) findViewById(R.id.image);
		title = (TextView) findViewById(R.id.text);
		layout2 = (LinearLayout) findViewById(R.id.secondlayout);
		layout3 = (LinearLayout) findViewById(R.id.thirdlayout);
		layout4 = (LinearLayout) findViewById(R.id.forth_layout);
		layout5 = (LinearLayout) findViewById(R.id.fifth_layout);
		layout6 = (LinearLayout) findViewById(R.id.six_layout);
		layout7 = (LinearLayout) findViewById(R.id.seven_layout);
		layout8 = (LinearLayout) findViewById(R.id.eight_layout);
		layout9 = (LinearLayout) findViewById(R.id.nine_layout);
		layout10 = (LinearLayout) findViewById(R.id.ten_layout);
		layout11 = (LinearLayout) findViewById(R.id.eleven_layout);
		layout12 = (LinearLayout) findViewById(R.id.twelv_layout);
		layout13 = (LinearLayout) findViewById(R.id.thirteen_layout);
		layout14 = (LinearLayout) findViewById(R.id.fourteen_layout);
		layout15 = (LinearLayout) findViewById(R.id.fifteen_layout);
		layout16 = (LinearLayout) findViewById(R.id.sixteen_layout);
		layout17 = (LinearLayout) findViewById(R.id.seventeen_layout);
		layout18 = (LinearLayout) findViewById(R.id.eighteen_layout);
		layout19 = (LinearLayout) findViewById(R.id.nineteen_layout);
		layout20 = (LinearLayout) findViewById(R.id.twinty_layout);

		facebook = (ImageButton) findViewById(R.id.fb);
		twitter = (ImageButton) findViewById(R.id.twitter);
		youtube = (ImageButton) findViewById(R.id.yt);

		home = (ImageButton) findViewById(R.id.menu);

		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SiteFilesActivity.this,
						MainList.class);
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

		layout2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(1);
			}
		});
		layout3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(2);
			}
		});
		layout4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(3);
			}
		});
		layout5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(4);
			}
		});
		layout6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(5);
			}
		});
		layout7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(6);
			}
		});
		layout8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(7);
			}
		});
		layout9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(8);
			}
		});
		layout10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(9);
			}
		});
		layout11.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(10);
			}
		});
		layout12.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(11);
			}
		});
		layout13.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(12);
			}
		});
		layout14.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(13);
			}
		});
		layout15.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(14);
			}
		});
		layout16.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(15);
			}
		});
		layout17.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(16);
			}
		});
		layout18.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(17);
			}
		});
		layout19.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(18);
			}
		});
		layout20.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openListItem(19);
			}
		});
	}

	private void openListItem(int listItemNumber) {
		Intent intent = new Intent(SiteFilesActivity.this, ListOfTitles.class);
		intent.putExtra("txt", txtTitle);
		intent.putExtra("img", imgres);
		intent.putExtra("vsd", listItemNumber);
        intent.putExtra("update",updateable);
		startActivity(intent);
		//finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// your code
//			Intent intent = new Intent(SiteFilesActivity.this,
//					ListOfTitles.class);
//			intent.putExtra("isFirstVisit", false);
//			startActivity(intent);
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
