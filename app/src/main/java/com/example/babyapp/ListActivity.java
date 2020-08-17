package com.example.babyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.babyapp.adapter.RecyclerViewAdapter;
import com.example.babyapp.data.DatabaseHandler;
import com.example.babyapp.model.Things;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {


    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;
    private DatabaseHandler databaseHandler;


    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton fab;
    private ArrayList<Things> thingsArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        fab=findViewById(R.id.fab_newItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpDialog();
            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHandler db=new DatabaseHandler(ListActivity.this);

        List<Things> thingsList=db.getAllThings();

        for(Things things: thingsList){
            thingsArrayList.add(things);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this,thingsArrayList);

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void createPopUpDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.activity_pop_up_window, null);

        babyItem = view.findViewById(R.id.babyItem);
        itemQuantity = view.findViewById(R.id.itemQunatity);
        itemColor = view.findViewById(R.id.itemColour);
        itemSize = view.findViewById(R.id.itemSize);
        saveButton = view.findViewById(R.id.saveButton);

        builder.setView(view);
        dialog= builder.create(); // creating our dialog object
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!babyItem.getText().toString().isEmpty()
                        && !itemColor.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()
                        && !itemSize.getText().toString().isEmpty()) {
                    saveItem(v);

                } else {
                    Snackbar.make(v, "Empty fields not allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }

        });

    }


    private void saveItem(View v) {
        Things item=new Things();
        String name=babyItem.getText().toString().trim();
        String color=itemColor.getText().toString().trim();
        DatabaseHandler db=new DatabaseHandler(ListActivity.this);

        int qty=Integer.parseInt(String.valueOf(itemQuantity.getText().toString().trim()));
        int size=Integer.parseInt(String.valueOf(itemSize.getText().toString().trim()));

        item.setName(name);
        item.setSize(String.valueOf(size));
        item.setColor(color);
        item.setQuantity(String.valueOf(qty));

        db.addObjects(item);
        Snackbar.make(v,"Item Saved",Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                     startActivity(new Intent(ListActivity.this,ListActivity.class));
                     finish();
            }
        },1200);



    }

}
