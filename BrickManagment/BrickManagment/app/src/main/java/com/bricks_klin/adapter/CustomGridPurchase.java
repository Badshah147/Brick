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

import com.bricks_klin.Util.SetterGetterP;
import com.bricks_klin.Util.SetterGetterSales;
import com.bricks_klin.brickmanagment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPc on 9/9/2017.
 */

public class CustomGridPurchase extends BaseAdapter {
    int it;
    private Context context;
    private SetterGetterP datafinal;
    private ViewHolder holder;
    LayoutInflater inflater;
    public List<SetterGetterP> data;
    private ArrayList<SetterGetterP> arraylistt;
    public CustomGridPurchase(List<SetterGetterP> selectgroups, Context c){
        context=c;
        data=selectgroups;
        this.arraylistt = new ArrayList<SetterGetterP>();
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
            holder.matname = (TextView) v.findViewById(R.id.tvhead);
            holder.date=(TextView)v.findViewById(R.id.tvleft);
            holder.price =(TextView)v.findViewById(R.id.tvright);
            holder.qty=(TextView)v.findViewById(R.id.tvbelowleft);
            holder.pby=(TextView)v.findViewById(R.id.tvbelowright);

            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }
        holder.IDD.setText(data.get(position).getId());
        holder.matname.setText("Name: "+data.get(position).getRawmat());
        holder.date.setText("date: "+data.get(position).getDate());
        holder.price.setText("Price: "+data.get(position).getPrice());
        holder.qty.setText("Qty: "+data.get(position).getQty());
        holder.pby.setText("Purchase by: "+data.get(position).getPby());
        return v;
    }

    private static class ViewHolder {
        TextView IDD;
        TextView matname;
        TextView date;
        TextView price;
        TextView qty;
        TextView pby;

    }
}
