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

import com.bricks_klin.Util.SetterGetterEmp;
import com.bricks_klin.brickmanagment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPc on 9/9/2017.
 */

public class EmpAdapter extends BaseAdapter
{
    int it;
    private Context context;
    private SetterGetterEmp datafinal;
    private ViewHolder holder;
    LayoutInflater inflater;
    public List<SetterGetterEmp> data;
    private ArrayList<SetterGetterEmp> arraylistt;
    public EmpAdapter(List<SetterGetterEmp> selectgroups, Context c){
        context=c;
        data=selectgroups;
        this.arraylistt = new ArrayList<SetterGetterEmp>();
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
            holder.name = (TextView) v.findViewById(R.id.tvhead);
            holder.salary=(TextView)v.findViewById(R.id.tvleft);
            holder.age=(TextView)v.findViewById(R.id.tvright);
            holder.cell=(TextView)v.findViewById(R.id.tvbelowleft);
            holder.addr=(TextView)v.findViewById(R.id.tvbelowright);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }
        holder.IDD.setText(data.get(position).getId());
        holder.name.setText(data.get(position).getName());
        holder.salary.setText("Salary: "+data.get(position).getSalary());
        holder.age.setText("Age: "+data.get(position).getAge());
        holder.cell.setText("Cell: "+data.get(position).getCell());
        holder.addr.setText("Address: "+data.get(position).getAddr());
        return v;
    }
    private static class ViewHolder {
        TextView IDD;
        TextView name;
        TextView salary;
        TextView age;
        TextView cell;
        TextView addr;
    }
}
