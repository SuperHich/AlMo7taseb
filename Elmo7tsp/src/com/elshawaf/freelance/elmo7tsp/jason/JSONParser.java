package com.elshawaf.freelance.elmo7tsp.jason;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
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
	File myInternalFile;
	File dir;
	Context context;

	// constructor
	public JSONParser(Context con) {
		// dir = new File(Environment.getExternalStorageDirectory(),
		// "Elmo7tsp");
		// dir.mkdirs();
		// myInternalFile = new File(Environment.getExternalStorageDirectory()
		// .toString() + "/Elmo7tsp/" + filename + ".txt");
		// try {
		// myInternalFile.createNewFile();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		context = con;
	}

	public JSONObject getJSONFromUrl(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

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
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// Toast.makeText(context, sb, Toast.LENGTH_LONG).show();
			}
			is.close();
			json = sb.toString();

			// try {
			//
			// FileOutputStream fos = new FileOutputStream(myInternalFile);
			// fos.write(json.getBytes("UTF-8"));
			// fos.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		return jObj;

	}
}