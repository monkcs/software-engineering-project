package com.example.covid_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin_block_Adapter extends RecyclerView.Adapter<Admin_block_Adapter.VersionVH> {

    private RequestQueue queue;
    List<Admin_block> Admin_block_List;



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


        holder.PersonText.setText(bookingblockis.getPersonen());
        holder.SvarText.setText(bookingblockis.getSvaret());
        holder.telefonText.setText(bookingblockis.getTelenmr());
        holder.datumTidText.setText(bookingblockis.getDatumTid());


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message;
                int Appointment;
                int ID;


                //hello

                Appointment = holder.getAdapterPosition();

                message = holder.medelande.getText().toString();
                holder.medelande.setText("");

          //      System.out.println("Hej detta är account: " + Admin_block_List.get(holder.getAdapterPosition()).getID());

                ID = Admin_block_List.get(holder.getAdapterPosition()).getID();


          //      System.out.println("Storlek: " + Admin_block_List.size());

                Admin_block_List.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

           //     System.out.println("Storlek: " + Admin_block_List.size());

                holder.medelande.onEditorAction(EditorInfo.IME_ACTION_DONE);    //stänger tagentbord



                bokaPendingDatabas(message, Appointment, ID);

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

                holder.medelande.onEditorAction(EditorInfo.IME_ACTION_DONE);    //stänger tagentbord

                avbokaPendingDatabas(message, Appointment);
            }
        });

        boolean isExpandable = Admin_block_List.get(position).getExpandable();
        holder.expandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }



    //
    //Calle o Charlie implementera
    //

    private void bokaPendingDatabas(String message, int appointment, Integer ID) {
        
        System.out.println("\n");
        System.out.println("--- Boka ---");

        System.out.println(message);

        System.out.println("Listnumber: " + appointment);
        System.out.println("ID: " + ID);

        System.out.println("---");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, WebRequest.urlbase + "provider/pending/approve.php",null,
                response -> {

            System.out.println("Hellpoasdfasdffddsvss");

                }, error -> {

            System.out.println("Error, den når inte fram");
            System.out.println(error.toString());

        }
        ) {
            @Override
            public Map<String, String> getParams()  {

                Map<String, String> params = new HashMap<>();
                params.put("account", "71");
                params.put("message", message);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
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


        TextView PersonText, SvarText, telefonText, datumTidText;
        EditText medelande;
        RelativeLayout expandable;
        LinearLayout linear;
        Button button, button2;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            PersonText = itemView.findViewById(R.id.Personen);
            SvarText = itemView.findViewById(R.id.Svaren);
            telefonText = itemView.findViewById(R.id.telefonNummer);
            datumTidText = itemView.findViewById(R.id.datumTid);
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

