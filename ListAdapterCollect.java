package com.example.cuongducnguyenkp.cdldemo2;

/**
 * Created by cuongducnguyen.kp on 10/11/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterCollect extends BaseAdapter {

    Context context;
    ArrayList<String> ID;
    ArrayList<String> Name;

    public ListAdapterCollect(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> name
    )
    {   this.context = context2;
        this.ID = id;
        this.Name = name;
      }

    public int getCount() {
        // TODO Auto-generated method stub
        return ID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            child = layoutInflater.inflate(R.layout.detail_service_type, null);

            holder = new Holder();

            holder.ID_TextView = (TextView) child.findViewById(R.id.texViewServiceTypeName);
            holder.Name_TextView = (TextView) child.findViewById(R.id.textViewServiceTypeCollect);
            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.ID_TextView.setText(ID.get(position));
        holder.Name_TextView.setText(" = "+Name.get(position));
        return child;
    }

    public class Holder {

        TextView ID_TextView;
        TextView Name_TextView;

    }

}