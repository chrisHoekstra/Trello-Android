package com.chrishoekstra.trello.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.vo.CardVO;

public class CardAdapter extends ArrayAdapter<CardVO> {

    static class ViewHolder {
        protected TextView nameText;

        protected HashMap<String, View> labels;

        protected LinearLayout descriptionBadge;

        protected LinearLayout commentBadge;
        protected TextView     commentBadgeCount;

        protected LinearLayout attachmentBadge;
        protected TextView     attachmentBadgeCount;

        protected LinearLayout checkItemBadge;
        protected TextView     checkItemBadgeCount;

        protected RelativeLayout voteBadge;
        protected TextView       voteBadgeCount;

        protected LinearLayout dueDateBadge;
        protected TextView     dueDateBadgeTime;

        protected LinearLayout notificationBadge;
        protected TextView     notificationBadgeCount;
        
        protected LinearLayout gravatarIcons;
    }

    private ArrayList<CardVO> mCards;
    private TrelloModel mModel;
    private LayoutInflater mInflater;
    private SimpleDateFormat mDateFomat;
    private DateTimeFormatter mDateTimeFormatter;
    
    private String mVoteString;
    private String mVotesString;
    
    private float mThirtyDp;
    private float mFourDp;

    public CardAdapter(Context context, int textViewResourceId, ArrayList<CardVO> cards, TrelloModel model) {
        super(context, textViewResourceId, cards);

        mCards = cards;
        mModel = model;
        mInflater = LayoutInflater.from(context);
        mDateFomat = new SimpleDateFormat("MMM dd");
        mDateTimeFormatter = ISODateTimeFormat.dateTime();

        mVoteString  = context.getResources().getString(R.string.vote);
        mVotesString = context.getResources().getString(R.string.votes);

        mThirtyDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mFourDp   = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  4, context.getResources().getDisplayMetrics());
    }

    public void updateCards(ArrayList<CardVO> cards) {
        mCards = cards;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.card_row, null);

            holder = new ViewHolder();

            holder.nameText = (TextView) convertView.findViewById(R.id.name);

            holder.labels = new HashMap<String, View>();
            holder.labels.put(CardVO.GREEN,  convertView.findViewById(R.id.green_label));
            holder.labels.put(CardVO.YELLOW, convertView.findViewById(R.id.yellow_label));
            holder.labels.put(CardVO.ORANGE, convertView.findViewById(R.id.orange_label));
            holder.labels.put(CardVO.RED,    convertView.findViewById(R.id.red_label));
            holder.labels.put(CardVO.PURPLE, convertView.findViewById(R.id.purple_label));
            holder.labels.put(CardVO.BLUE,   convertView.findViewById(R.id.blue_label));

            holder.descriptionBadge = (LinearLayout) convertView.findViewById(R.id.descriptionBadgeLayout);

            holder.commentBadge      = (LinearLayout) convertView.findViewById(R.id.commentBadgeLayout);
            holder.commentBadgeCount = (TextView)     convertView.findViewById(R.id.commentBadgeCount);

            holder.attachmentBadge      = (LinearLayout) convertView.findViewById(R.id.attachmentBadgeLayout);
            holder.attachmentBadgeCount = (TextView)     convertView.findViewById(R.id.attachmentBadgeCount);

            holder.checkItemBadge      = (LinearLayout) convertView.findViewById(R.id.checkItemBadgeLayout);
            holder.checkItemBadgeCount = (TextView)     convertView.findViewById(R.id.checkItemBadgeCount);

            holder.voteBadge      = (RelativeLayout) convertView.findViewById(R.id.voteBadgeLayout);
            holder.voteBadgeCount = (TextView)     convertView.findViewById(R.id.voteBadgeCount);

            holder.dueDateBadge      = (LinearLayout) convertView.findViewById(R.id.dueDateBadgeLayout);
            holder.dueDateBadgeTime = (TextView)     convertView.findViewById(R.id.dueDateBadgeTime);

            holder.notificationBadge      = (LinearLayout) convertView.findViewById(R.id.notificationBadgeLayout);
            holder.notificationBadgeCount = (TextView)     convertView.findViewById(R.id.notificationBadgeCount);
            
            holder.gravatarIcons = (LinearLayout) convertView.findViewById(R.id.gravatar_icons);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        for (Entry<String, View> labelEntry : holder.labels.entrySet()) {
            labelEntry.getValue().setVisibility(View.GONE);
        }

        CardVO card = mCards.get(position);

        if (card != null) {
            holder.nameText.setText(card.name);

            for (String label : card.labels) {
                holder.labels.get(label).setVisibility(View.VISIBLE);
            }

            holder.descriptionBadge.setVisibility(card.badges.description ? View.VISIBLE : View.GONE);

            holder.commentBadge.setVisibility(card.badges.comments > 0 ? View.VISIBLE : View.GONE);
            holder.commentBadgeCount.setText(card.badges.comments + "");

            holder.attachmentBadge.setVisibility(card.badges.attachments > 0 ? View.VISIBLE : View.GONE);
            holder.attachmentBadgeCount.setText(card.badges.attachments + "");

            holder.checkItemBadge.setVisibility(card.badges.checkItems > 0 ? View.VISIBLE : View.GONE);
            holder.checkItemBadgeCount.setText(card.badges.checkItemsChecked + "/" + card.badges.checkItems);

            holder.voteBadge.setVisibility(card.idMembersVoted.size() > 0 ? View.VISIBLE : View.GONE);
            holder.voteBadgeCount.setText(card.idMembersVoted.size() + " " + 
                    (card.idMembersVoted.size() > 1 ? mVotesString : mVoteString));

            holder.dueDateBadge.setVisibility(((card.badges.due != null) && (!card.badges.due.equals(""))) ? View.VISIBLE : View.GONE);
            try {
                holder.dueDateBadgeTime.setText(((card.badges.due != null) && (!card.badges.due.equals(""))) ? mDateFomat.format(mDateTimeFormatter.parseDateTime(card.badges.due).toDate()) : "");
            } catch (Exception e) {
                e.printStackTrace();
                Log.w("Fucked up date", card.badges.due);
                holder.dueDateBadgeTime.setText("?");
            }

            int count = mModel.getNotificationCount(card._id);
            holder.notificationBadge.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
            holder.notificationBadgeCount.setText(count + "");
            
            if ((card.idMembers != null) && 
                (card.idMembers.size() > 0)) {
                holder.gravatarIcons.setVisibility(View.VISIBLE);
                holder.gravatarIcons.removeAllViews();
                
                for (String id : card.idMembers) {
                    ImageView gravatar = (ImageView) mInflater.inflate(R.layout.gravatar_icon, null);
                    holder.gravatarIcons.addView(gravatar);
                    gravatar.getLayoutParams().height = (int) mThirtyDp;
                    gravatar.getLayoutParams().width  = (int) mThirtyDp;
                }
            } else {
                holder.gravatarIcons.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}
