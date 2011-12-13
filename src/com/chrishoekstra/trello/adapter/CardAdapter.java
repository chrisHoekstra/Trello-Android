package com.chrishoekstra.trello.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chrishoekstra.trello.R;
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
        
        protected LinearLayout voteBadge;
        protected TextView     voteBadgeCount;
    }
    
    public ArrayList<CardVO> mCards;
    private LayoutInflater mInflater;
    private String mVoteString;
    private String mVotesString;
    
    public CardAdapter(Context context, int textViewResourceId, ArrayList<CardVO> cards) {
        super(context, textViewResourceId, cards);

        mInflater = LayoutInflater.from(context);
        mCards = cards;
        
        mVoteString  = context.getResources().getString(R.string.vote);
        mVotesString = context.getResources().getString(R.string.votes);
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

            holder.voteBadge      = (LinearLayout) convertView.findViewById(R.id.voteBadgeLayout);
            holder.voteBadgeCount = (TextView)     convertView.findViewById(R.id.voteBadgeCount);
            
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
            holder.commentBadgeCount.setText(card.badges.comments);
            
            holder.attachmentBadge.setVisibility(card.badges.attachments > 0 ? View.VISIBLE : View.GONE);
            holder.attachmentBadgeCount.setText(card.badges.attachments);

            holder.checkItemBadge.setVisibility(card.badges.checkItems > 0 ? View.VISIBLE : View.GONE);
            holder.checkItemBadgeCount.setText(card.badges.checkItemsChecked + "/" + card.badges.checkItems);

            holder.voteBadge.setVisibility(card.idMembersVoted.size() > 0 ? View.VISIBLE : View.GONE);
            holder.voteBadgeCount.setText(card.idMembersVoted.size() + " " + 
                    (card.idMembersVoted.size() > 1 ? mVotesString : mVoteString));
        }
        
        return convertView;
    }
}
