package com.chrishoekstra.trello.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.Utils;
import com.chrishoekstra.trello.vo.NotificationVO;

public class NotificationsAdapter extends ArrayAdapter<NotificationVO> {

    public List<NotificationVO> mNotifications;
    private LayoutInflater mInflater;

    static class ViewHolder {
        protected TextView notificationText;
        protected TextView dateText;
        protected ImageView gravatar;
    }
    
    public NotificationsAdapter(Context context, int textViewResourceId, List<NotificationVO> notifications) {
        super(context, textViewResourceId, notifications);

        mInflater = LayoutInflater.from(context);
        mNotifications = notifications;
    }
    
    public void updateNotifications(List<NotificationVO> notifications) {
        mNotifications = notifications;
        notifyDataSetChanged();
    }

    public void addNotifications(List<NotificationVO> notifications) {
        mNotifications.addAll(notifications);
        notifyDataSetChanged();
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.notification_row, null);

            holder = new ViewHolder();
            holder.notificationText = (TextView)  convertView.findViewById(R.id.notification_text);
            holder.dateText         = (TextView)  convertView.findViewById(R.id.date);
            holder.gravatar         = (ImageView) convertView.findViewById(R.id.gravatar);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NotificationVO notification = mNotifications.get(position);
        
        if (notification != null) {
            holder.notificationText.setText(Utils.getHtmlFormattedNotificationText(notification));
            holder.dateText.setText(Utils.getFormattedStringFromDate(notification.date));
        }
        
        return convertView;
    }
}
