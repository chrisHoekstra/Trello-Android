package com.chrishoekstra.trello.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.vo.CardVO;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends ArrayAdapter<CardVO> {

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
        TextView nameText;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.card_row, null);

            nameText = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(R.id.name, nameText);
        } else {
            nameText = (TextView) convertView.getTag(R.id.name);
        }

        CardVO card = mCards.get(position);

        if (card != null) {
            nameText.setText(card.name);
            initLabels(convertView, card.labels);
        }

        return convertView;
    }

    private void initLabels(View convertView, List<String> labels) {

        View labelsView = convertView.findViewById(R.id.labels);
        TextView labelGreen = (TextView) labelsView.findViewById(R.id.labelGreen);
        TextView labelYellow = (TextView) labelsView.findViewById(R.id.labelYellow);
        TextView labelOrange = (TextView) labelsView.findViewById(R.id.labelOrange);
        TextView labelRed = (TextView) labelsView.findViewById(R.id.labelRed);
        TextView labelBlue = (TextView) labelsView.findViewById(R.id.labelBlue);
        TextView labelPurple = (TextView) labelsView.findViewById(R.id.labelPurple);

        boolean hasGreen = false;
        boolean hasYellow = false;
        boolean hasOrange = false;
        boolean hasRed = false;
        boolean hasBlue = false;
        boolean hasPurple = false;

        for (String label : labels) {
            if (label.equals("green")) {
                hasGreen = true;
            }
            if (label.equals("yellow")) {
                hasYellow = true;
            }
            if (label.equals("orange")) {
                hasOrange = true;
            }
            if (label.equals("red")) {
                hasRed = true;
            }
            if (label.equals("blue")) {
                hasBlue = true;
            }
            if (label.equals("purple")) {
                hasPurple = true;
            }

        }

        if (!hasGreen) {
            labelGreen.setVisibility(View.INVISIBLE);
            labelGreen.setWidth(0);
            ((LinearLayout) labelGreen.getParent()).setPadding(0, 0, 0, 0);

        } else {
            labelGreen.setVisibility(View.VISIBLE);
            labelGreen.setWidth(20);
            labelGreen.setHeight(5);
            ((LinearLayout) labelGreen.getParent()).setPadding(5, 0, 0, 0);

        }
        if (!hasYellow) {
            labelYellow.setVisibility(View.INVISIBLE);
            labelYellow.setWidth(0);
            ((LinearLayout) labelYellow.getParent()).setPadding(0, 0, 0, 0);

        } else {
            labelYellow.setVisibility(View.VISIBLE);
            labelYellow.setWidth(20);
            labelYellow.setHeight(5);
            ((LinearLayout) labelYellow.getParent()).setPadding(5, 0, 0, 0);

        }
        if (!hasOrange) {
            labelOrange.setVisibility(View.INVISIBLE);
            labelOrange.setWidth(0);
            ((LinearLayout) labelOrange.getParent()).setPadding(0, 0, 0, 0);

        } else {
            labelOrange.setVisibility(View.VISIBLE);
            labelOrange.setWidth(20);
            labelOrange.setHeight(5);
            ((LinearLayout) labelOrange.getParent()).setPadding(5, 0, 0, 0);

        }
        if (!hasRed) {
            labelRed.setVisibility(View.INVISIBLE);
            labelRed.setWidth(0);
            ((LinearLayout) labelRed.getParent()).setPadding(0, 0, 0, 0);

        } else {
            labelRed.setVisibility(View.VISIBLE);
            labelRed.setWidth(20);
            labelRed.setHeight(5);
            ((LinearLayout) labelRed.getParent()).setPadding(5, 0, 0, 0);

        }
        if (!hasBlue) {
            labelBlue.setVisibility(View.INVISIBLE);
            labelBlue.setWidth(0);
            ((LinearLayout) labelBlue.getParent()).setPadding(0, 0, 0, 0);

        } else {
            labelBlue.setVisibility(View.VISIBLE);
            labelBlue.setWidth(20);
            labelBlue.setHeight(5);
            ((LinearLayout) labelBlue.getParent()).setPadding(5, 0, 0, 0);

        }
        if (!hasPurple) {
            labelPurple.setVisibility(View.INVISIBLE);
            labelPurple.setWidth(0);
        } else {
            labelPurple.setVisibility(View.VISIBLE);
            labelPurple.setWidth(20);
            labelPurple.setHeight(5);

        }
    }
}
