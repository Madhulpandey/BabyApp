package com.example.babyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.babyapp.data.DatabaseHandler;
import com.example.babyapp.model.Things;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;
    private DatabaseHandler databaseHandler;

    private ImageButton addItemsBtn;
    DatabaseHandler db=new DatabaseHandler(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemsBtn=findViewById(R.id.add_image_btn);

        byPassActivity();
        List<Things> thingsList=db.getAllThings();

        for(Things thing :thingsList){
            Log.d("Main", "onCreate: "+thing.getName());

        }

        addItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPopupDialog();

            }
        });

        //byPassActivity();
    }

    private void byPassActivity() {
    if(db.getItemCount()>0){
        startActivity(new Intent(MainActivity.this,ListActivity.class));
        finish();
    }
    }

    private void createPopupDialog() {

        builder =new AlertDialog.Builder(this);
        View view =getLayoutInflater().inflate(R.layout.activity_pop_up_window,null);

        babyItem =view.findViewById(R.id.babyItem);
        itemQuantity=view.findViewById(R.id.itemQunatity);
        itemColor=view.findViewById(R.id.itemColour);
        itemSize=view.findViewById(R.id.itemSize);
        saveButton=view.findViewById(R.id.saveButton);

        builder.setView(view);
        dialog= builder.create(); // creating our dialog object
        dialog.show();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!babyItem.getText().toString().isEmpty()
                        && !itemColor.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()
                        && !itemSize.getText().toString().isEmpty()){
                    saveItem(v);

                }
                else
                {
                    Snackbar.make(v,"Empty fields not allowed",Snackbar.LENGTH_SHORT)
                            .show();
                }

            }

        });


    }

    public void saveItem(View view){
        Things item=new Things();
        String name=babyItem.getText().toString().trim();
        String color=itemColor.getText().toString().trim();

        int qty=Integer.parseInt(String.valueOf(itemQuantity.getText().toString().trim()));
        int size=Integer.parseInt(String.valueOf(itemSize.getText().toString().trim()));

        item.setName(name);
        item.setSize(String.valueOf(size));
        item.setColor(color);
        item.setQuantity(String.valueOf(qty));

        db.addObjects(item);
        Snackbar.make(view,"Item Saved",Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        },1200);


    }
}
