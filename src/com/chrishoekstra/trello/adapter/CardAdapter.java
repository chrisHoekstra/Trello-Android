package com.chrishoekstra.trello.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.vo.CardVO;

public class CardAdapter extends ArrayAdapter<CardVO> {
    
    static class ViewHolder {
        protected TextView nameText;
        
        protected HashMap<String, View> labels;
        
        protected View greenLabel;
        protected View yellowLabel;
        protected View orangeLabel;
        protected View redLabel;
        protected View purpleLabel;
        protected View blueLabel;
    }
    
    public ArrayList<CardVO> mCards;
    private LayoutInflater mInflater;
    
    public CardAdapter(Context context, int textViewResourceId, ArrayList<CardVO> cards) {
        super(context, textViewResourceId, cards);

        mInflater = LayoutInflater.from(context);
        mCards = cards;
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
        }
        
        return convertView;
    }
}
