package com.acpitzone.gulatikirasoi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class offerAdapter extends RecyclerView.Adapter<offerAdapter.offerViewModel> {

    private List<offers> offerslist;

    public offerAdapter(List<offers>offerslist) {
       this.offerslist = offerslist;
    }

    @NonNull
    @Override
    public offerViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer,parent,false);

        return new offerViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull offerViewModel holder, int position) {
        offers offers;
        offers = offerslist.get(position);

        holder.offerName.setText(offerslist.get(position).getOfferN());
        holder.offerCode.setText(offerslist.get(position).getOfferC());
    }

    public void upDate(List<offers> upDate){
        offerslist.clear();
        offerslist.addAll(upDate);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return offerslist.size();
    }



    public class offerViewModel extends RecyclerView.ViewHolder{
        TextView offerName, offerCode;
        public offerViewModel(@NonNull View itemView) {

            super(itemView);

            offerName = itemView.findViewById(R.id.offerName);
            offerCode = itemView.findViewById(R.id.code);
        }
    }
}
