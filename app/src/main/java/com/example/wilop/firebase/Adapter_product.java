package com.example.wilop.firebase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by wilop on 21/05/2018.
 */

public class Adapter_product extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<String> Ids = new ArrayList<String>();
    ArrayList<String> Names = new ArrayList<String>();
    ArrayList<String> Prices = new ArrayList<String>();
    ArrayList<String> Description = new ArrayList<String>();
    ArrayList<String> Urls = new ArrayList<String>();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().getRoot().child("Products");
    int y;

    public Adapter_product(Context context, ArrayList<String> Names,ArrayList<String> Prices , ArrayList<String> Description,ArrayList<String> Urls,ArrayList<String> Ids){
        this.context = context;
        this.Ids = Ids;
        this.Names = Names;
        this.Prices = Prices;
        this.Description = Description;
        this.Urls = Urls;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        y = i;
        final View vista = inflater.inflate(R.layout.product,null);
        TextView name = vista.findViewById(R.id.p_name);
        final TextView price = vista.findViewById(R.id.p_price);
        final TextView description = vista.findViewById(R.id.p_description);
        ImageView imagen = vista.findViewById(R.id.p_imagen);
        name.setText(Names.get(i));
        price.setText(Prices.get(i));
        description.setText(Description.get(i));

        //String to URI
        Uri urlpicture = Uri.parse(Urls.get(i));
        //Load the imagen from Uri
        Glide.with(vista)
                //Dowload the imagen
            .load(urlpicture)
                //Load into the ImageView
            .into(imagen);

        vista.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("Delete")
                        .setMessage("You want to delete the product?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Log.i("PRODU",Ids.get(y));
                                db.child(Ids.get(y)).removeValue();
                                Names.remove(y);
                                Prices.remove(y);
                                Urls.remove(y);
                                Ids.remove(y);
                                Description.remove(y);
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                return false;
            }
        });


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
