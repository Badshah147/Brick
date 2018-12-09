/*******************************************************************************
 * Copyright (c) 2017. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential.
 * Written by Mian Muhammad Usman <usman.saif22@gmail.com>, August 2017
 *
 *  Programmer Info:
 *  FullName: Mian Muhammad Usman
 *  E-Mail: usman.saif22@gmail.com
 ******************************************************************************/

package com.bricks_klin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bricks_klin.Util.SetterGetter;
import com.bricks_klin.brickmanagment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPc on 9/9/2017.
 */

public class CustomGrid extends BaseAdapter {
    private Context context;
    private SetterGetter datafinal;
    private ViewHolder v;
    public List<SetterGetter> data;
    private ArrayList<SetterGetter> arraylistt;

    public CustomGrid(List<SetterGetter> selectgroups, Context c) {
        context = c;
        data = selectgroups;
        this.arraylistt = new ArrayList<SetterGetter>();
        this.arraylistt.addAll(data);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View list;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            list = new View(context);
            list = inflater.inflate(R.layout.list_item, null);

            v = new ViewHolder();
            v.ID = (TextView) list.findViewById(R.id.idd);
            v.Name = (TextView) list.findViewById(R.id.matname);
            v.Date = (TextView) list.findViewById(R.id.matdate);
            v.Price = (TextView) list.findViewById(R.id.matprice);

            datafinal = (SetterGetter) data.get(position);
            v.ID.setText(datafinal.getID());
            v.Name.setText(datafinal.getRawname());
            v.Date.setText(datafinal.getDate());
            v.Price.setText(datafinal.getPrice());

            list.setTag(datafinal);
        } else {
            list = (View) convertView;
        }

        return list;
    }

    private static class ViewHolder {
        TextView ID;
        TextView Name;
        TextView Date;
        TextView Price;

    }
}
