package com.chrishoekstra.trello.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.vo.BoardVO;

public class BoardAdapter extends ArrayAdapter<BoardVO> {

    public ArrayList<BoardVO> mBoards;
    private LayoutInflater mInflater;
    
    public BoardAdapter(Context context, int textViewResourceId, ArrayList<BoardVO> boards) {
        super(context, textViewResourceId, boards);

        mInflater = LayoutInflater.from(context);
        mBoards = boards;
    }
    
    public void updateCards(ArrayList<BoardVO> boards) {
        mBoards = boards;
        notifyDataSetChanged();
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView nameText;
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.board_row, null);
            
            nameText = (TextView) convertView.findViewById(R.id.name);
            
            convertView.setTag(R.id.name, nameText);
        } else {
            nameText = (TextView) convertView.getTag(R.id.name);
        }

        BoardVO board = mBoards.get(position);
        
        if (board != null) {
            nameText.setText(board.name);
        }
        
        return convertView;
    }
}
