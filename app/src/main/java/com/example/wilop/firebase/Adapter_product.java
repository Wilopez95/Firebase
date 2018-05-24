package com.example.wilop.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wilop on 21/05/2018.
 */

public class Adapter_product extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<String> Names = new ArrayList<String>();
    ArrayList<String> Prices = new ArrayList<String>();
    ArrayList<String> Description = new ArrayList<String>();

    public Adapter_product(Context context, ArrayList<String> Names,ArrayList<String> Prices , ArrayList<String> Description){
        this.context = context;
        this.Names = Names;
        this.Prices = Prices;
        this.Description = Description;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.product,null);
        TextView name = vista.findViewById(R.id.p_name);
        TextView price = vista.findViewById(R.id.p_price);
        TextView description = vista.findViewById(R.id.p_description);
        name.setText(Names.get(i));
        price.setText(Prices.get(i));
        description.setText(Description.get(i));

        return vista;
    }

    @Override
    public int getCount() {
        return Names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
