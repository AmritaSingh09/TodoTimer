package com.example.todotimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModal> modalList;
    private Context context;

    public TodoAdapter(List<TodoModal> modalList, Context context) {
        this.modalList = modalList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoModal modal = modalList.get(position);

        holder.title.setText(modal.title);
        holder.desc.setText(modal.description);
        holder.status.setText(modal.status);

    }

    @Override
    public int getItemCount() {
        return (modalList != null)? modalList.size() : 0;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, desc, status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.t_title);
            desc = itemView.findViewById(R.id.t_desc);
            status = itemView.findViewById(R.id.t_status);
        }
    }
}
