package com.example.covid_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class age_change_block_Adapter  extends RecyclerView.Adapter<age_change_block_Adapter.VersionVH> {

    List<age_change_block> List;

    public age_change_block_Adapter(List<age_change_block> List) {
        this.List = List;
    }

    @NonNull
    @Override
    public age_change_block_Adapter.VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_row_agechange, parent, false);
        return new age_change_block_Adapter.VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull age_change_block_Adapter.VersionVH holder, int position) {


        age_change_block blockis = List.get(position);

        holder.datumText.setText(blockis.getDatumText());
        holder.listaageText.setText(blockis.getListaageText());


        boolean isExpandable = List.get(position).getExpandable();
        holder.expandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {


        TextView datumText, listaageText;
        RelativeLayout expandable;
        LinearLayout linear;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            datumText = itemView.findViewById(R.id.Datum_age);
            listaageText = itemView.findViewById(R.id.list_agy);


            linear = itemView.findViewById(R.id.linear_layout_row_age);
            expandable = itemView.findViewById(R.id.expandable_layout_row_age);



            linear.setOnClickListener(view -> {
                age_change_block blockis = List.get(getAdapterPosition());
                blockis.setExpandable(!blockis.getExpandable());
                notifyItemChanged(getAdapterPosition());
            });

        }
    }


}
