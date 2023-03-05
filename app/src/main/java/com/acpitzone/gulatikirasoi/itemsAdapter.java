package com.acpitzone.gulatikirasoi;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.itemViewHolder> {

    private List<item> ItemsList;
    private SelectedListner listner;



    public itemsAdapter(List<item> ItemsList, SelectedListner listner) {
        this.ItemsList = ItemsList;
        this.listner = listner;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, @SuppressLint("RecyclerView") int position) {

        item item;
        item = ItemsList.get(position);

        holder.nameDish.setText(ItemsList.get(position).getName());
        holder.priceDish.setText(ItemsList.get(position).getPrice());
        holder.typeDish.setText(ItemsList.get(position).getType());
//        holder.dishImg.setImageResource(ItemsList.get(position).getImage());
        Glide.with(holder.itemView.getContext()).load(item.getImage()).into(holder.dishImg);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String x = holder.addT.getText().toString();
                String max = "+";

                if(!x.equals("Add")){
                    int i = Integer.parseInt(x);
                    holder.addT.setText(""+ (i+1));
                    listner.onItemClicked(ItemsList.get(position),i+1, max);
                }

                else{
                    int i =0;
                    holder.addT.setText(""+ (i+1));
                    listner.onItemClicked(ItemsList.get(position),i+1,max);
                }
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String x = holder.addT.getText().toString();
                String min = "-";

                if(x.equals("1")||x.equals("Add")){

                    holder.addT.setText("Add");
                    listner.onItemClicked(ItemsList.get(position),0, min);
                }

                else{
                    int i = Integer.parseInt(x);
                    holder.addT.setText(""+ (i-1));
                    listner.onItemClicked(ItemsList.get(position),i-1, min);
                }

            }
        });
    }

    public void upDate(List<item> upDate){
        ItemsList.clear();
        ItemsList.addAll(upDate);

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

   public class itemViewHolder extends RecyclerView.ViewHolder{

        TextView nameDish, priceDish, typeDish;
        ImageView dishImg;
        ImageButton add,sub;
        TextView addT;


        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            nameDish = itemView.findViewById(R.id.dishName);
            priceDish = itemView.findViewById(R.id.dishPrice);
            typeDish = itemView.findViewById(R.id.vegNonveg);
            dishImg = itemView.findViewById(R.id.itemImg);
            add = itemView.findViewById(R.id.btn_add);
            sub = itemView.findViewById(R.id.btn_sub);
            addT = itemView.findViewById(R.id.addT);
        }
    }
}


