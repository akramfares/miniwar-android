package com.faresgames.miniwar;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationAdapter extends BaseAdapter {

	ArrayList<Notification> notifications;
	LayoutInflater inflater;
	Context c;
	public NotificationAdapter(Context context,ArrayList<Notification> notifications) {
		c = context;
		inflater = LayoutInflater.from(context);
		this.notifications = notifications;
	}
	
	@Override
	public int getCount() {
		return notifications.size();
	}

	@Override
	public Object getItem(int position) {
		return notifications.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {

		LinearLayout notification;
		ImageView image_notification;
		TextView tvMessage;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.notificationitem, null);
			holder.notification = (LinearLayout)convertView.findViewById(R.id.notification);
			holder.image_notification = (ImageView)convertView.findViewById(R.id.image_notification);
			holder.tvMessage = (TextView)convertView.findViewById(R.id.tvMessage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(notifications.get(position).getType().equals("test")){
			holder.image_notification.setImageDrawable(c.getResources().getDrawable(R.drawable.gold_add));
			//holder.notification.setBackgroundColor(Color.YELLOW);
		}
		if(notifications.get(position).getType().equals("gold_up")){
			holder.image_notification.setImageDrawable(c.getResources().getDrawable(R.drawable.gold_up));
		}
		String message = notifications.get(position).getMessage();
		String date = notifications.get(position).getDate();
		holder.tvMessage.setText(Html.fromHtml(message+"<br/><small><font color=\"grey\">"+humanTiming(Integer.parseInt(date))+"</font><small>"));

		return convertView;
	}
	
	public String humanTiming (int time)
	{

	    time = (int) (System.currentTimeMillis()/1000) - time; // to get the time since that moment
	    if(time > 31536000){
			int numberOfUnits = (int) (time / 31536000);
			return numberOfUnits+" year"+((numberOfUnits>1)?"s":"");
		}
		if(time > 2592000){
			int numberOfUnits = (int) (time / 2592000);
			return numberOfUnits+" month"+((numberOfUnits>1)?"s":"");
		}
		if(time > 604800){
			int numberOfUnits = (int) (time / 604800);
			return numberOfUnits+" week"+((numberOfUnits>1)?"s":"");
		}
		if(time > 86400){
			int numberOfUnits = (int) (time / 86400);
			return numberOfUnits+" day"+((numberOfUnits>1)?"s":"");
		}
		if(time > 3600){
			int numberOfUnits = (int) (time / 3600);
			return numberOfUnits+" hour"+((numberOfUnits>1)?"s":"");
		}
		if(time > 60){
			int numberOfUnits = (int) (time / 60);
			return numberOfUnits+" minute"+((numberOfUnits>1)?"s":"");
		}
		return time+" seconds";
	}

}
