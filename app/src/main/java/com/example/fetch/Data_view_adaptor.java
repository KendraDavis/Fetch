package com.example.fetch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Data_view_adaptor extends RecyclerView.Adapter<Data_view_adaptor.MyViewHolder> {

    Context context;
    ArrayList<DataModel> model;
    public Data_view_adaptor(Context context, ArrayList<DataModel> model){
        this.context=context;
        this.model = model;
    }
    @NonNull
    @Override
    public Data_view_adaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.r_view_row, parent, false);

        return new Data_view_adaptor.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Data_view_adaptor.MyViewHolder holder, int position) {
        //assign values
        DataModel current = model.get(position);

        holder.name.setText(current.getName());
        holder.listId.setText(String.valueOf(current.getListId()));
        holder.id.setText(String.valueOf(current.getId()));

        if (current.getListId() == 1) {
            holder.card.setCardBackgroundColor(context.getColor(R.color.color1));
        } else if (current.getListId() == 2) {
            holder.card.setCardBackgroundColor(context.getColor(R.color.color2));
        } else if (current.getListId() == 3) {
            holder.card.setCardBackgroundColor(context.getColor(R.color.color3));
        } else if (current.getListId() == 4){
            holder.card.setCardBackgroundColor(context.getColor(R.color.color4));
        }

    }

    @Override
    public int getItemCount() {
        //how many to display
        return model.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, id, listId;
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            listId = itemView.findViewById(R.id.listId);
            id = itemView.findViewById(R.id.id);
            card = itemView.findViewById(R.id.card);
        }
    }
}
