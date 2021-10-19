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

public class Dosage_block_adapter extends RecyclerView.Adapter<Dosage_block_adapter.VersionVH> {

    List<Dosage_block> Dosage_block_List;
    private RequestQueue queue;



    public Dosage_block_adapter(List<Dosage_block> Dosage_block_List) {
        this.Dosage_block_List = Dosage_block_List;

    }

    @NonNull
    public Dosage_block_adapter.VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dosage_row, parent, false);

        queue = Volley.newRequestQueue(view.getContext());

        return new Dosage_block_adapter.VersionVH(view);
    }


    public void onBindViewHolder(@NonNull Dosage_block_adapter.VersionVH holder, int position) {

        Dosage_block blockis = Dosage_block_List.get(position);


        holder.Text.setText(" " + blockis.getNamn());
        holder.Text2.setText(blockis.getAntal() + " ");

    }





    public int getItemCount() {
        return Dosage_block_List.size();
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
