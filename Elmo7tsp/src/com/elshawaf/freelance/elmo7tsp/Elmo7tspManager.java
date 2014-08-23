package com.elshawaf.freelance.elmo7tsp;

import android.content.Context;
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
	
}