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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bricks_klin.Util.SetterGetterAttendance;
import com.bricks_klin.Util.SetterGetterW;
import com.bricks_klin.brickmanagment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPc on 9/9/2017.
 */

public class CustomGridAttendance extends BaseAdapter {
    int it;
    private Context context;
    private SetterGetterAttendance datafinal;
    private ViewHolder holder;
    LayoutInflater inflater;
    public List<SetterGetterAttendance> data;
    private ArrayList<SetterGetterAttendance> arraylistt;
    public CustomGridAttendance(List<SetterGetterAttendance> selectgroups, Context c){
        context=c;
        data=selectgroups;
        this.arraylistt = new ArrayList<SetterGetterAttendance>();
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
    public View getView(int position, View convertView, ViewGroup parent){
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v =convertView;
        if (v == null) {

            holder=new ViewHolder();
            v=inflater.inflate(R.layout.list_item_attendance,null);
            holder.empname = (TextView) v.findViewById(R.id.tvempname);
            holder.date = (TextView) v.findViewById(R.id.tvdateee);
            holder.value=(TextView)v.findViewById(R.id.tvpresent);

            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }
        holder.empname.setText(""+data.get(position).getEmpname());
        holder.date.setText(""+data.get(position).getDate());
        if (data.get(position).getValue().equals("Absent")){
            holder.value.setTextColor(Color.RED);
            holder.value.setText(""+data.get(position).getValue());
        }else {
            holder.value.setText(""+data.get(position).getValue());
        }


        return v;
    }

    private static class ViewHolder {
        TextView empname;
        TextView date;
        TextView value;


    }
}
