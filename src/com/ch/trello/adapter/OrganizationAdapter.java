package com.ch.trello.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ch.trello.R;
import com.ch.trello.vo.BoardVO;
import com.ch.trello.vo.OrganizationVO;

import java.util.ArrayList;


public class OrganizationAdapter extends ArrayAdapter<OrganizationVO> {

    public ArrayList<OrganizationVO> mOrganizations;
    private LayoutInflater mInflater;

    public OrganizationAdapter(Context context, int textViewResourceId, ArrayList<OrganizationVO> organizations) {
        super(context, textViewResourceId, organizations);

        mInflater = LayoutInflater.from(context);
        mOrganizations = organizations;
    }

    public void updateOrganizations(ArrayList<OrganizationVO> organizations) {
        mOrganizations = organizations;
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

        OrganizationVO organization = mOrganizations.get(position);

        if (organization != null) {
            nameText.setText(organization.name);
        }

        return convertView;
    }
}
