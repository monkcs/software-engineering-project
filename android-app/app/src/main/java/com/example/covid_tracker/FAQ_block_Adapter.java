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

public class FAQ_block_Adapter extends RecyclerView.Adapter<FAQ_block_Adapter.VersionVH> {

    List<FAQ_block> FAQ_block_List;

    public FAQ_block_Adapter(List<FAQ_block> FAQ_block_List) {
        this.FAQ_block_List = FAQ_block_List;
    }

    @NonNull
    @Override
    public VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionVH holder, int position) {


        FAQ_block faqblockis = FAQ_block_List.get(position);

        holder.QuestionText.setText(faqblockis.getQuestion());
        holder.AnswerText.setText(faqblockis.getAnswer());

        boolean isExpandable = FAQ_block_List.get(position).getExpandable();
        holder.expandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return FAQ_block_List.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {


        TextView QuestionText, AnswerText;
        RelativeLayout expandable;
        LinearLayout linear;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            QuestionText = itemView.findViewById(R.id.Question);
            AnswerText = itemView.findViewById(R.id.Svar);


           linear = itemView.findViewById(R.id.linear_layout_row);
           expandable = itemView.findViewById(R.id.expandable_layout_row);



           linear.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   FAQ_block faqblockis = FAQ_block_List.get(getAdapterPosition());
                   faqblockis.setExpandable(!faqblockis.getExpandable());
                   notifyItemChanged(getAdapterPosition());
               }
           });

        }
    }
}