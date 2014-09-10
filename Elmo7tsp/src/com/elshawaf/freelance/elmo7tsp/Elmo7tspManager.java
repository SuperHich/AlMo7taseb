package com.elshawaf.freelance.elmo7tsp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Elmo7tspManager {

	static final String TAG = "Elmo7tspManager";
	
	private static Elmo7tspManager mInstance = null;
	private static SharedPreferences settings;
	private SharedPreferences.Editor editor;
	
	public Elmo7tspManager(Context context) {
		
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		editor = settings.edit();
	}

	public synchronized static Elmo7tspManager getInstance(Context context) {
		if (mInstance == null)
			mInstance = new Elmo7tspManager(context);

		return mInstance;
	}
	
	public void setFirstVisit(boolean state){
		editor.putBoolean("firstVisit", state);
		editor.commit();
	}
	
	public boolean isFirstVisit(){
		return settings.getBoolean("firstVisit", true);
	}
	
	/**
	 * Display a dialog that user has no network connection
	 * 
	 * @param ctx1
	 * 
	 */
	public static void showNoConnectionDialog(Context context) {

		final Context ctx = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setCancelable(false);
		builder.setMessage(context.getResources().getString(R.string.network_msg));
		// builder.setTitle("showNoConnectionDialog");
		builder.setPositiveButton(context.getResources().getString(R.string.wifi),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent wifiCnt = new Intent(
						android.provider.Settings.ACTION_WIFI_SETTINGS);
				ctx.startActivity(wifiCnt);
				((Activity) ctx).finish();
			}
		});

		builder.setNeutralButton(
				context.getResources().getString(R.string.roaming),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Mobile data
						Intent intent = new Intent();
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setAction(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
						ctx.startActivity(intent);
						((Activity) ctx).finish();
					}
				});

		builder.setNegativeButton(context.getResources().getString(R.string.exit),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((Activity) ctx).finish();
			}
		});

		builder.show();
	}

	
}