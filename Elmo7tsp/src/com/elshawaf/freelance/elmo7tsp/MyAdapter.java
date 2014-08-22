package com.elshawaf.freelance.elmo7tsp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private static ArrayList<ItemDetails> itemDetailsrrayList;
	LayoutInflater l_inflater;
	private Integer[] imgid = { R.drawable.pic13, R.drawable.pic6,
			R.drawable.pic3, R.drawable.pic2, R.drawable.pic4, R.drawable.pic5, 
			R.drawable.pic7,R.drawable.hesba,R.drawable.ic_launcher };

	public MyAdapter(Context context, ArrayList<ItemDetails> results) {
		itemDetailsrrayList = results;
		l_inflater = LayoutInflater.from(context);

	}

	static class ViewHolder {
		TextView itemname;
		ImageView itemImage;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder;
		if (convertView == null) {
			convertView = l_inflater.inflate(R.layout.listrow, null);
			viewholder = new ViewHolder();
			viewholder.itemImage = (ImageView) convertView
					.findViewById(R.id.image);
			viewholder.itemname = (TextView) convertView
					.findViewById(R.id.text);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();

		}
		viewholder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getImageNumber()-1]);
		viewholder.itemname.setText(itemDetailsrrayList.get(position).getName());		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemDetailsrrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemDetailsrrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
