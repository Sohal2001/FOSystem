package com.ordering.food.fosystem.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ordering.food.fosystem.R;

public class CustomAdapter extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] menName;
    private final Integer[] menPrice;

    public CustomAdapter(Activity context, String[] menName, Integer[] menPrice) {

        super(context, R.layout.activity_image_list,menName);
        this.context = context;
        this.menName = menName;
        this.menPrice = menPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();

        View rowView=inflater.inflate(R.layout.activity_image_list,null,true);

        TextView textView=(TextView)rowView.findViewById(R.id.textViewlist);
        TextView textViewPrice=(TextView)rowView.findViewById(R.id.textViewPriceList);

        textView.setText(menName[position]);
        textViewPrice.setText(menPrice[position]);

        return rowView;
    }
}
