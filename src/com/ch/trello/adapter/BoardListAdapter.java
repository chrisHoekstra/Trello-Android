package com.ch.trello.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ch.trello.R;
import com.ch.trello.vo.BoardListVO;

public class BoardListAdapter extends ArrayAdapter<BoardListVO> {

    public ArrayList<BoardListVO> mBoardLists;
    private LayoutInflater mInflater;
    
    public BoardListAdapter(Context context, int textViewResourceId, ArrayList<BoardListVO> boardLists) {
        super(context, textViewResourceId, boardLists);

        mInflater = LayoutInflater.from(context);
        mBoardLists = boardLists;
    }
    
    public void updateCards(ArrayList<BoardListVO> boardLists) {
        mBoardLists = boardLists;
        notifyDataSetChanged();
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView nameText;
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.board_list_row, null);
            
            nameText = (TextView)  convertView.findViewById(R.id.name);
            
            convertView.setTag(R.id.name, nameText);
        } else {
            nameText = (TextView)  convertView.getTag(R.id.name);
        }

        BoardListVO boardList = mBoardLists.get(position);
        
        if (boardList != null) {
            nameText.setText(boardList.name);
        }
        
        return convertView;
    }
}
