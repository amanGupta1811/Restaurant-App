package com.acpitzone.gulatikirasoi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class coupenAdapter extends RecyclerView.Adapter<coupenAdapter.coupenViewHolder> {

    List<coupen> coupenList;
    CouponListner listner;

    public coupenAdapter(List<coupen> coupenList, CouponListner listner) {
        this.coupenList = coupenList;
        this.listner = listner;
    }

    @NonNull
    @Override
    public coupenAdapter.coupenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupen,parent,false);
        return new coupenViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull coupenAdapter.coupenViewHolder holder, int position) {
        coupen coupen;

        coupen = coupenList.get(position);
        holder.offerName.setText(coupen.getOfferName1());
        holder.offerCode.setText(coupen.getOfferCode1());

        holder.tapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onCouponClick(coupenList.get(position));
            }
        });

    }
    @Override
    public int getItemCount() {
        return coupenList.size();
    }
    public void upDate(List<coupen> upDate){
        coupenList.clear();
        coupenList.addAll(upDate);

        notifyDataSetChanged();
    }
    public class coupenViewHolder extends RecyclerView.ViewHolder{
        TextView offerName, offerCode;
        Button tapBtn;
        public coupenViewHolder(@NonNull View itemView) {
            super(itemView);
            offerName = itemView.findViewById(R.id.offerName1);
            offerCode = itemView.findViewById(R.id.code1);
            tapBtn = itemView.findViewById(R.id.tap);
        }
    }
}
