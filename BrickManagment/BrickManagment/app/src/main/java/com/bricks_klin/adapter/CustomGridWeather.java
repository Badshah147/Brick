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
import android.widget.ImageView;
import android.widget.TextView;

import com.bricks_klin.Util.SetterGetterP;
import com.bricks_klin.Util.SetterGetterW;
import com.bricks_klin.brickmanagment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPc on 9/9/2017.
 */

public class CustomGridWeather extends BaseAdapter {
    int it;
    private Context context;
    private SetterGetterW datafinal;
    private ViewHolder holder;
    LayoutInflater inflater;
    public List<SetterGetterW> data;
    private ArrayList<SetterGetterW> arraylistt;
    public CustomGridWeather(List<SetterGetterW> selectgroups, Context c){
        context=c;
        data=selectgroups;
        this.arraylistt = new ArrayList<SetterGetterW>();
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
            v=inflater.inflate(R.layout.list_item_sales,null);
            holder.IDD = (TextView) v.findViewById(R.id.tvidd);
            holder.value = (TextView) v.findViewById(R.id.tvvalue);
            holder.degree=(TextView)v.findViewById(R.id.tvdegree);
            holder.locname =(TextView)v.findViewById(R.id.tvlocation);
            holder.wday=(TextView)v.findViewById(R.id.tvday);
            holder.imv=(ImageView) v.findViewById(R.id.imv);

            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }
        holder.IDD.setText(data.get(position).getId());
        if (data.get(position).getValue().equals("1")){
            holder.value.setText("Rainy");
            holder.imv.setImageResource(R.drawable.rain);
        }else if (data.get(position).getValue().equals("2")){
            holder.value.setText("Intermittant Clouds");
            holder.imv.setImageResource(R.drawable.sun);
        }else if (data.get(position).getValue().equals("3")){
            holder.value.setText("Rainy");
            holder.imv.setImageResource(R.drawable.sunny);
        }else {

        }
        holder.degree.setText("Degree: "+data.get(position).getDegree());
        holder.locname.setText(""+data.get(position).getLocname());
        holder.wday.setText(""+data.get(position).getWday());
        holder.value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }

    private static class ViewHolder {
        TextView IDD;
        TextView value;
        TextView degree;
        TextView locname;
        TextView wday;
        ImageView imv;

    }
}
