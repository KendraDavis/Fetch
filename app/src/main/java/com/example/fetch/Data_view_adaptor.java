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

    /**
     * Constructor set the global variables
     *
     * @param context The context for which the adaptor was called
     * @param model The DataModel ArrayList belonging to the recyclerView being created
     */
    public Data_view_adaptor(Context context, ArrayList<DataModel> model){
        this.context=context;
        this.model = model;
    }

    /**
     * Inflates the view the correct cards
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return a new adaptor
     */
    @NonNull
    @Override
    public Data_view_adaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.r_view_row, parent, false);

        return new Data_view_adaptor.MyViewHolder(view);
    }

    /**
     * Updates/assigns values to each card displayed
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull Data_view_adaptor.MyViewHolder holder, int position) {
        DataModel current = model.get(position);

        String preId = "List ID: " + current.getListId();
        holder.name.setText( current.getName());
        holder.listId.setText(preId);

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

    /**
     * @return the total number of items in model
     */
    @Override
    public int getItemCount() {
        return model.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, listId;
        CardView card;

        /**
         * Assigns the different view variables so that they can be set
         *
         * @param itemView the view associated with the activity calling the Data_view_adaptor
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            listId = itemView.findViewById(R.id.listId);
            card = itemView.findViewById(R.id.card);
        }
    }
}
