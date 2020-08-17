package com.example.babyapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyapp.R;
import com.example.babyapp.data.DatabaseHandler;
import com.example.babyapp.model.Things;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Things> thingsList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    DatabaseHandler db= new DatabaseHandler(context);

    public RecyclerViewAdapter(Context context, List<Things> thingsList) {
        this.context = context;
        this.thingsList = thingsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(context)
                .inflate(R.layout.objects_row,parent,false);


        return new ViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Things things= thingsList.get(position);
        holder.name_card.setText("Name: "+things.getName());
        holder.color_card.setText("Color:"+things.getColor());
        holder.size_card.setText("Size:"+things.getSize());
        holder.qty_card.setText("Qty:"+things.getQuantity());

    }


    @Override
    public int getItemCount() {
        return thingsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name_card;
        public TextView qty_card;
        public TextView color_card;
        public TextView size_card;
        public TextView date_card;

        public ImageButton delete_card;
        public ImageButton update_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_card=itemView.findViewById(R.id.name_card);
            qty_card=itemView.findViewById(R.id.qty_card);
            color_card=itemView.findViewById(R.id.color_card);
            size_card=itemView.findViewById(R.id.size_card);
            delete_card=itemView.findViewById(R.id.deleteBtn_card);
            update_card=itemView.findViewById(R.id.updateBtn_card);

            delete_card.setOnClickListener(this);
            update_card.setOnClickListener(this);

            int pos=getAdapterPosition();
        }

        @Override
        public void onClick(View v) {

            switch(v.getId()){


                case R.id.deleteBtn_card:
                    //Log.d("delete Test", "onClick: "+getAdapterPosition());
                    int position=getAdapterPosition();
                    Things thing=thingsList.get(position);
                    deleteItem(thing.getId());
                    break;
                case R.id.updateBtn_card:
                    editItem();
                    break;

            }

        }
        private void editItem() {

        }

        private void deleteItem(final int adapterPosition) {

            builder=new AlertDialog.Builder(context);

            inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.confirmation_popup,null);

            Button noButton=view.findViewById(R.id.conf_no_button);
            Button yesButton=view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db=new DatabaseHandler(context);
                    db.deleteThings(adapterPosition);

                    thingsList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();

                }
            });

         }
    }

}
