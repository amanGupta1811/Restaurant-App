package com.acpitzone.gulatikirasoi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.cartViewHolder> {

    private List<car> carList = new ArrayList<>();

    public cartAdapter(List<car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public cartAdapter.cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart,parent,false);
        return new cartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartAdapter.cartViewHolder holder, int position) {
        car car;
        car = carList.get(position);

        holder.d_Name.setText(car.getdName());
        holder.d_Price.setText(car.getdPrice());

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class cartViewHolder extends RecyclerView.ViewHolder {
        TextView d_Name, d_Price;
        public cartViewHolder(@NonNull View itemView) {
            super(itemView);

            d_Name = itemView.findViewById(R.id.d_Name);
            d_Price = itemView.findViewById(R.id.d_Price);
        }
    }
}
