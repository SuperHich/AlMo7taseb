package com.elshawaf.freelance.elmo7tsp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EncyclopediaActivity extends Activity {

	ImageView image;
	TextView title, text, txtUrl;
	LinearLayout layout1, layout2;
	int imgres;
	String txtTitle;
	ImageButton facebook, twitter, youtube, home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.encyclopedia_activity);
		image = (ImageView) findViewById(R.id.image);
		title = (TextView) findViewById(R.id.text);
		text = (TextView) findViewById(R.id.textView1);
		//txtUrl = (TextView) findViewById(R.id.txt_url);

		facebook = (ImageButton) findViewById(R.id.fb);
		twitter = (ImageButton) findViewById(R.id.twitter);
		youtube = (ImageButton) findViewById(R.id.yt);
		home = (ImageButton) findViewById(R.id.menu);
/*
		txtUrl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://www.alhesba.com/"));
				startActivity(browserIntent);

			}
		});
*/
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EncyclopediaActivity.this,
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

		image.setImageResource(imgres);
		title.setText(txtTitle);
		text.setText("\n أول وأكبر عمل موسوعي يجمع ما صدر عن علماء ودعاة الإسلام في الحسبة."
				+ "من هنا يمكنكم الوصول إلى الكتب القديمة والحديثة والرسائل الجامعية والمجلات والمقالات المتعلقة بالحسبة ومطالعتها وتحميلها، كما يمكنكم سماع ومشاهدة وتحميل عدد كبير من المحاضرات والدروس والخطب في شتى المجالات الاحتسابية."
				+ "ونقدم للمتخصصين خدمة رائعة عبر قاعدة البيانات، حيث يمكنهم الاطلاع على بيانات الكتب والرسائل الجامعية المؤلفة في الحسبة للاستعانة بها في الأبحاث وإعداد الرسائل الجامعية وتأليف الكتب"

        + "\n http://www.alhesba.com \n");

        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setMovementMethod(new ScrollingMovementMethod());
        Linkify.addLinks(text, Linkify.ALL);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// your code
//			Intent intent = new Intent(EncyclopediaActivity.this,
//					MainList.class);
//			intent.putExtra("isFirstVisit", false);
//			startActivity(intent);
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
