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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin_block_Adapter extends RecyclerView.Adapter<Admin_block_Adapter.VersionVH> {

    List<Admin_block> Admin_block_List;
    private RequestQueue queue;


    public Admin_block_Adapter(List<Admin_block> Admin_block_List) {
        this.Admin_block_List = Admin_block_List;

    }

    @NonNull
    @Override
    public Admin_block_Adapter.VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_inbox_row, parent, false);

        queue = Volley.newRequestQueue(view.getContext());

        return new Admin_block_Adapter.VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_block_Adapter.VersionVH holder, int position) {

        Admin_block bookingblockis = Admin_block_List.get(position);


        holder.PersonText.setText(bookingblockis.getPersonen() + " " + bookingblockis.getPersonen2());
        holder.SvarText.setText(bookingblockis.getSvaret());
        holder.telefonText.setText(bookingblockis.getTelenmr());
        holder.datumTidText.setText(bookingblockis.getDatumTid());


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int Appointment;
                int ID;


                Appointment = holder.getAdapterPosition();


                //      System.out.println("Hej detta är account: " + Admin_block_List.get(holder.getAdapterPosition()).getID());

                ID = Admin_block_List.get(holder.getAdapterPosition()).getID();


                //      System.out.println("Storlek: " + Admin_block_List.size());

                Admin_block_List.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                //     System.out.println("Storlek: " + Admin_block_List.size());


                bokaPendingDatabas(Appointment, ID);

            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int Appointment;
                int ID;

                Appointment = holder.getAdapterPosition();


                ID = Admin_block_List.get(holder.getAdapterPosition()).getID();

                //    System.out.println("Storlek: " + Admin_block_List.size());

                Admin_block_List.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                //   System.out.println("Storlek: " + Admin_block_List.size());


                avbokaPendingDatabas(ID, Appointment);
            }
        });

        boolean isExpandable = Admin_block_List.get(position).getExpandable();
        holder.expandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    private void bokaPendingDatabas(int appointment, Integer ID) {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/pending/approve.php",
                response -> {
                }, error -> {
        }
        ) {
            @Override
            public Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("account", ID.toString());
                params.put("message", "Pending to booking");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
    }

    private void avbokaPendingDatabas(Integer ID, int appointment) {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/pending/decline.php",
                response -> {
                }, error -> {
        }
        ) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("account", ID.toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
    }


    @Override
    public int getItemCount() {
        return Admin_block_List.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {


        TextView PersonText, SvarText, telefonText, datumTidText;
        RelativeLayout expandable;
        LinearLayout linear;
        Button button, button2;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            PersonText = itemView.findViewById(R.id.Personen);

            SvarText = itemView.findViewById(R.id.Svaren);
            telefonText = itemView.findViewById(R.id.telefonNummer);
            datumTidText = itemView.findViewById(R.id.datumTid);


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

