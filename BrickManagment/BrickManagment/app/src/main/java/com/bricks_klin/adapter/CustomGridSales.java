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
import com.bricks_klin.Util.SetterGetterEmp;
import com.bricks_klin.Util.SetterGetterSales;
import com.bricks_klin.brickmanagment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPc on 9/9/2017.
 */

public class CustomGridSales extends BaseAdapter {
    int it;
    private Context context;
    private SetterGetterSales datafinal;
    private ViewHolder holder;
    LayoutInflater inflater;
    public List<SetterGetterSales> data;
    private ArrayList<SetterGetterSales> arraylistt;
    public CustomGridSales(List<SetterGetterSales> selectgroups, Context c){
        context=c;
        data=selectgroups;
        this.arraylistt = new ArrayList<SetterGetterSales>();
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
            v=inflater.inflate(R.layout.list_item_emp,null);
            holder.IDD = (TextView) v.findViewById(R.id.tvidd);
            holder.brickqty = (TextView) v.findViewById(R.id.tvhead);
            holder.price=(TextView)v.findViewById(R.id.tvleft);
            holder.date =(TextView)v.findViewById(R.id.tvright);
            holder.saleby=(TextView)v.findViewById(R.id.tvbelowleft);
            holder.saleto=(TextView)v.findViewById(R.id.tvbelowright);

            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }
        holder.IDD.setText(data.get(position).getID());
        holder.brickqty.setText("Qty: "+data.get(position).getS_brick_qty());
        holder.price.setText("Price: "+data.get(position).getS_price());
        holder.date.setText("Date: "+data.get(position).getS_date());
        holder.saleby.setText("Sale by: "+data.get(position).getS_by());
        holder.saleto.setText("Sale to: "+data.get(position).getS_to());
        return v;
    }

    private static class ViewHolder {
        TextView IDD;
        TextView brickqty;
        TextView price;
        TextView date;
        TextView saleby;
        TextView saleto;

    }
}
