package com.example.wilop.firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView ListaProductos;
    EditText Pname,Pdescription,Pprice;
    Button save_product;
    ConstraintLayout add_product;
    ArrayList<String> Names = new ArrayList<String>();
    ArrayList<String> Prices = new ArrayList<String>();
    ArrayList<String> Description = new ArrayList<String>();
    JSONObject jsonObject;
    String value;
    Context context = this;
    FloatingActionButton fab;
    FirebaseDatabase database;
    StorageReference mStorageRef;
    DatabaseReference myRef;
    DatabaseReference myData;

    DatabaseReference myDatabasereference;
    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        Pname = findViewById(R.id.p_name);
        Pprice = findViewById(R.id.p_price);
        Pdescription = findViewById(R.id.p_description);
        save_product = findViewById(R.id.save_data);
        add_product = findViewById(R.id.add_product);
        ListaProductos = findViewById(R.id.list_products);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.INVISIBLE);
                ListaProductos.setVisibility(View.INVISIBLE);
                add_product.setVisibility(View.VISIBLE);
            }
        });

        save_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =  Pname.getText().toString();
                String price = Pprice.getText().toString();
                String description = Pdescription.getText().toString();
                if(name.isEmpty()|| price.isEmpty() || description.isEmpty()){
                    Snackbar.make(v, "Invalid data", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    myRef = database.getReference("Products");
                    Map<String, String> map = new HashMap<>();
                    map.put("Name",name);
                    map.put("Price",price);
                    map.put("Description",description);
                    myRef.push().setValue(map);
                    Toast.makeText(getApplicationContext(), "Add new product",
                            Toast.LENGTH_SHORT).show();
                    //fab.setVisibility(View.VISIBLE);
                    //ListaProductos.setVisibility(View.VISIBLE);
                    //add_product.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });


        Names.clear();
        Prices.clear();
        Description.clear();

        //----------------------
        myDatabasereference = FirebaseDatabase.getInstance().getReference();
        myData = myDatabasereference.child("Products");
        myData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = ds.child("Name").getValue(String.class);
                    Names.add(name);
                    String price = ds.child("Price").getValue(String.class);
                    Prices.add(price+"₡");
                    String description = ds.child("Description").getValue(String.class);
                    Description.add(description);
                    Log.d("DATOS", name + " / " + description + " / " + price);
                }
                ListaProductos.setAdapter(new Adapter_product(context,Names,Prices,Description));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            fab.setVisibility(View.VISIBLE);
            ListaProductos.setVisibility(View.VISIBLE);
            add_product.setVisibility(View.INVISIBLE);
        }
        return true;
    }

}
