package com.example.covid_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class DigitalHealthBlockAdapter extends RecyclerView.Adapter<DigitalHealthBlockAdapter.VersionVH> {

    List<DigitalHealthBlock> DigitalHealthBlock;
    private RequestQueue queue;



    public DigitalHealthBlockAdapter(List<DigitalHealthBlock> DigitalHealthBlock) {
        this.DigitalHealthBlock = DigitalHealthBlock;

    }

    @NonNull
    public DigitalHealthBlockAdapter.VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dosage_row, parent, false);

        queue = Volley.newRequestQueue(view.getContext());

        return new DigitalHealthBlockAdapter.VersionVH(view);
    }


    public void onBindViewHolder(@NonNull DigitalHealthBlockAdapter.VersionVH holder, int position) {

        DigitalHealthBlock blockis = DigitalHealthBlock.get(position);


        holder.Text.setText(" " + blockis.getFirst());
        holder.Text2.setText(blockis.getSecond() + " ");

    }





    public int getItemCount() {
        return DigitalHealthBlock.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {


        TextView Text, Text2;


        public VersionVH(@NonNull View itemView) {
            super(itemView);

            Text = itemView.findViewById(R.id.Doser);
            Text2 = itemView.findViewById(R.id.Doser2);

        }
    }

}

