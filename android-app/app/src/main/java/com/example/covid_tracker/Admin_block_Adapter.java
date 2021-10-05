package com.example.covid_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message;
                int Appointment;

                Appointment = holder.getAdapterPosition();

                message = holder.medelande.getText().toString();
                holder.medelande.setText("");

          //      System.out.println("Storlek: " + Admin_block_List.size());

                Admin_block_List.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

           //     System.out.println("Storlek: " + Admin_block_List.size());


                bokaPendingDatabas(message, Appointment);

            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message;
                int Appointment;

                Appointment = holder.getAdapterPosition();

                message = holder.medelande.getText().toString();

                holder.medelande.setText("");
            //    System.out.println("Storlek: " + Admin_block_List.size());

                Admin_block_List.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

             //   System.out.println("Storlek: " + Admin_block_List.size());


                avbokaPendingDatabas(message, Appointment);
            }
        });

        boolean isExpandable = Admin_block_List.get(position).getExpandable();
        holder.expandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }



    //
    //Calle o Charlie implementera
    //

    private void bokaPendingDatabas(String message, int appointment) {

        System.out.println("\n");
        System.out.println("--- Boka ---");

        System.out.println(message);

        System.out.println("Listnumber: " + appointment);

        System.out.println("---");
    }

    //
    //Calle o Charlie implementera
    //

    private void avbokaPendingDatabas(String message, int appointment) {

        System.out.println("\n");
        System.out.println("--- Avboka ---");

        System.out.println(message);

        System.out.println("Listnumber: " + appointment);

        System.out.println("---");
    }

    @Override
    public int getItemCount() {
        return Admin_block_List.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {


        TextView tidText, dosText, platsText;
        EditText medelande;
        RelativeLayout expandable;
        LinearLayout linear;
        Button button, button2;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            tidText = itemView.findViewById(R.id.Tid);
            dosText = itemView.findViewById(R.id.Dos);
            platsText = itemView.findViewById(R.id.Plats);
            medelande = itemView.findViewById(R.id.medelande);


            linear = itemView.findViewById(R.id.linear_layout_admin_row);
            expandable = itemView.findViewById(R.id.expandable_layout_admin_row);
            button = itemView.findViewById(R.id.CardViewAdmin);
            button2 = itemView.findViewById(R.id.CardViewAdmin2);




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

