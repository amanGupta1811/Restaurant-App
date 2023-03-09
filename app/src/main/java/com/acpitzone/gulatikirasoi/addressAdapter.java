package com.acpitzone.gulatikirasoi;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class addressAdapter extends RecyclerView.Adapter<addressAdapter.addressViewHolder> {

    List<addressData> addressDataList;
    addressListner listner;

    public addressAdapter(List<addressData> addressDataList, addressListner listner) {
        this.addressDataList = addressDataList;
        this.listner = listner;
    }

    @NonNull
    @Override
    public addressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address,parent,false);
        return new addressAdapter.addressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addressViewHolder holder, @SuppressLint("RecyclerView") int position) {
        addressData addressData;

        addressData = addressDataList.get(position);

        holder.saveAl.setText(addressData.getSaveAl());
        holder.deliveryAl.setText(addressData.getDeliveryAl());
        holder.completeAl.setText(addressData.getCompleteAl());
        holder.cityAl.setText(addressData.getCityAl());

        holder.selectAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onAddressListner(addressDataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressDataList.size();
    }

    public void upDate(List<addressData> upDate){
        addressDataList.clear();
        addressDataList.addAll(upDate);

        notifyDataSetChanged();
    }

    public class addressViewHolder extends RecyclerView.ViewHolder {

        TextView saveAl, deliveryAl, completeAl, cityAl;
        Button selectAl;
        public addressViewHolder(@NonNull View itemView) {
            super(itemView);

            saveAl = itemView.findViewById(R.id.saveAl);
            deliveryAl = itemView.findViewById(R.id.deliveryAl);
            completeAl = itemView.findViewById(R.id.completeAl);
            cityAl = itemView.findViewById(R.id.cityAl);
            selectAl = itemView.findViewById(R.id.selectAl);
        }
    }
}
