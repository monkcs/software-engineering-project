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

public class Booking_block_Adapter extends RecyclerView.Adapter<Booking_block_Adapter.VersionVH> {

    List<Booking_block> Booking_block_List;

    public Booking_block_Adapter(List<Booking_block> Booking_block_List) {
        this.Booking_block_List = Booking_block_List;
    }

    @NonNull
    @Override
    public Booking_block_Adapter.VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_card_row, parent, false);
        return new Booking_block_Adapter.VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Booking_block_Adapter.VersionVH holder, int position) {


        Booking_block bookingblockis = Booking_block_List.get(position);

        holder.tidText.setText(bookingblockis.getTid());
        holder.dosText.setText(bookingblockis.getDos());
        holder.platsText.setText(bookingblockis.getPlats());


        boolean isExpandable = Booking_block_List.get(position).getExpandable();
        holder.expandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return Booking_block_List.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {


        TextView tidText, dosText, platsText;
        RelativeLayout expandable;
        LinearLayout linear;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            tidText = itemView.findViewById(R.id.Tid);
            dosText = itemView.findViewById(R.id.Dos);
            platsText = itemView.findViewById(R.id.Plats);


            linear = itemView.findViewById(R.id.linear_layout_booking_row);
            expandable = itemView.findViewById(R.id.expandable_layout_booking_row);



            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Booking_block bookingblockis = Booking_block_List.get(getAdapterPosition());
                    bookingblockis.setExpandable(!bookingblockis.getExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }

}
