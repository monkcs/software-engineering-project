package com.example.covid_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Admin_block_Adapter extends RecyclerView.Adapter<Admin_block_Adapter.VersionVH> {

    List<Admin_block> Admin_block_List;

    public Admin_block_Adapter(List<Admin_block> Admin_block_List) {
        this.Admin_block_List = Admin_block_List;
    }

    @NonNull
    @Override
    public Admin_block_Adapter.VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_inbox_row, parent, false);
        return new Admin_block_Adapter.VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_block_Adapter.VersionVH holder, int position) {


       Admin_block bookingblockis = Admin_block_List.get(position);

        holder.tidText.setText(bookingblockis.getTid());
        holder.dosText.setText(bookingblockis.getDos());
        holder.platsText.setText(bookingblockis.getPlats());



        boolean isExpandable = Admin_block_List.get(position).getExpandable();
        holder.expandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return Admin_block_List.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {


        TextView tidText, dosText, platsText;
        RelativeLayout expandable;
        LinearLayout linear;
        Button button, button2;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            tidText = itemView.findViewById(R.id.Tid);
            dosText = itemView.findViewById(R.id.Dos);
            platsText = itemView.findViewById(R.id.Plats);


            linear = itemView.findViewById(R.id.linear_layout_admin_row);
            expandable = itemView.findViewById(R.id.expandable_layout_admin_row);
            button = itemView.findViewById(R.id.CardViewAdmin);
            button2 = itemView.findViewById(R.id.CardViewAdmin2);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Boka: " + getAdapterPosition());

                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Neka: " + getAdapterPosition());


                }
            });




            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Admin_block Adminblockis = Admin_block_List.get(getAdapterPosition());
                    Adminblockis.setExpandable(!Adminblockis.getExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }

}

