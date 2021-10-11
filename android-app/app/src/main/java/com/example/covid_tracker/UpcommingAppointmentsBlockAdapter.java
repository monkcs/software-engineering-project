package com.example.covid_tracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UpcommingAppointmentsBlockAdapter extends RecyclerView.Adapter<UpcommingAppointmentsBlockAdapter.VersionVH> {

    List<UpcommingAppointmentsBlock> UA_block_list;
    private final Context context;

    public UpcommingAppointmentsBlockAdapter(List<UpcommingAppointmentsBlock> UA_block_list, Context context) {
        this.UA_block_list = UA_block_list;
        this.context = context;
    }

    @NonNull
    @Override
    public VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upc_appoint_row, parent, false);
        return new VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionVH holder, int position) {

        UpcommingAppointmentsBlock ua_block = UA_block_list.get(position);

        holder.Time.setText(ua_block.getTime());
        holder.LastName.setText(ua_block.getLastname());
        holder.FirstName.setText(ua_block.getFirstname());


        boolean isExpandable = UA_block_list.get(position).getExpandable();
        holder.expandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return UA_block_list.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {

        TextView Time, LastName, FirstName;
        Button btnInfo;
        RelativeLayout expandable;
        LinearLayout linear;

        public VersionVH(@NonNull View itemView) {
            super(itemView);


            Time = itemView.findViewById(R.id.Time);
            LastName = itemView.findViewById(R.id.LastName);
            FirstName = itemView.findViewById(R.id.FirstName);

            btnInfo = itemView.findViewById(R.id.ua_btn_info);

            linear = itemView.findViewById(R.id.linear_layout_row_ua);
            expandable = itemView.findViewById(R.id.expandable_layout_row_ua);

            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpcommingAppointmentsBlock ua_block = UA_block_list.get(getAdapterPosition());
                    ua_block.setExpandable(!ua_block.getExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fullName = LastName.getText().toString() + "," + FirstName.getText().toString();
                    Intent i1 = new Intent(context, HandlePerson.class);
                    /*functionality to send data between activities*/
                    i1.putExtra("key",fullName);
                    context.startActivity(i1);
                }
            });

        }
    }
}
