package com.elshawaf.freelance.elmo7tsp;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutActivity extends Activity {

	ImageView image;
	TextView title;
	LinearLayout layout1, layout2;
	int imgres;
	String txtTitle;
	ImageButton facebook, twitter, youtube, home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
//		image = (ImageView) findViewById(R.id.image);
//		title = (TextView) findViewById(R.id.text);
//
//		facebook = (ImageButton) findViewById(R.id.fb);
//		twitter = (ImageButton) findViewById(R.id.twitter);
//		youtube = (ImageButton) findViewById(R.id.yt);
//		home = (ImageButton) findViewById(R.id.menu);
//
//		home.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(AboutActivity.this, MainList.class);
//				intent.putExtra("isFirstVisit", false);
//				startActivity(intent);
//				finish();
//			}
//		});
//
//		facebook.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
//						.parse("https://www.facebook.com/almohtasb1?fref=ts"));
//				startActivity(browserIntent);
//
//			}
//		});
//		twitter.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
//						.parse("https://twitter.com/@al_mohtasb1"));
//				startActivity(browserIntent);
//
//			}
//		});
//		youtube.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
//						.parse("http://www.youtube.com/user/almohtasb1"));
//				startActivity(browserIntent);
//
//			}
//		});
//		Bundle bundle = getIntent().getExtras();
//		imgres = bundle.getInt("img");
//		txtTitle = bundle.getString("txt");
//
//		image.setImageResource(imgres);
//		title.setText(txtTitle);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// your code
//			Intent intent = new Intent(AboutActivity.this, MainList.class);
//			intent.putExtra("isFirstVisit", false);
//			startActivity(intent);
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
