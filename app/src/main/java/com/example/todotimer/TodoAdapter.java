package com.example.todotimer;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModal> modalList;
    private Context context;
    LocalDatabaseHelper helper;

    public TodoAdapter(List<TodoModal> modalList, Context context, LocalDatabaseHelper helper) {
        this.modalList = modalList;
        this.context = context;
        this.helper = helper;
    }

    public TodoAdapter() {
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
        holder.time_lft.setText(((modal.time_left/60) < 10 ?"0"+modal.time_left/60:(modal.time_left/60))+":"+ ((modal.time_left%60)<10? ("0"+modal.time_left%60) : (modal.time_left%60)));

        if (modal.time_left == modal.duration){
            holder.timeer.setVisibility(View.VISIBLE);
            holder.status.setText("TODO");
        }else if (modal.time_left == 0){
            holder.timeer.setVisibility(View.GONE);
            holder.status.setText("DONE");
        }else {
            holder.timeer.setVisibility(View.VISIBLE);
            holder.status.setText("IN-PROGRESS");
        }

        holder.t_timer.setText(((modal.duration/60) < 10 ?"0"+modal.duration/60:(modal.duration/60))+":"+ ((modal.duration%60)<10? ("0"+modal.duration%60) : (modal.duration%60)));



        holder.play.setOnClickListener(vi ->{
            holder.countDownTimer = new CountDownTimer(modal.time_left * 1000,1000){
                @Override
                public void onTick(long l) {
                    //long seconds = l/1000;
                    //long minutes = seconds/60;
                    modal.time_left = (int) l/1000;//Integer.parseInt(String.valueOf(l));
                    //Toast.makeText(context, "Long returns : mins"+(l/1000)+" : seconds "+(l/60000), Toast.LENGTH_SHORT).show();
                    //holder.time_lft.setText(l/60000 + ":" + l/1000);
                    holder.time_lft.setText(((modal.time_left/60) < 10 ?"0"+modal.time_left/60:(modal.time_left/60))+":"+ ((modal.time_left%60)<10? ("0"+modal.time_left%60) : (modal.time_left%60)));

                }

                @Override
                public void onFinish() {
                    holder.timeer.setVisibility(View.GONE);
                    holder.status.setText("DONE");
                    modal.time_left = 0;
                }
            }.start();
            holder.play.setVisibility(View.GONE);
            holder.pause.setVisibility(View.VISIBLE);

        });

        holder.pause.setOnClickListener(vi ->{
            //todo update to db
            holder.countDownTimer.cancel();
            holder.play.setVisibility(View.VISIBLE);
            holder.pause.setVisibility(View.GONE);
            helper.updateDataToDatabase(modal);
            Toast.makeText(context, "Time left = "+ modal.time_left, Toast.LENGTH_SHORT).show();
        });

        holder.remove.setOnClickListener(v -> {
            modalList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, modalList.size());

            synchronized (this){
                helper.removeOneTodo(modal.id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (modalList != null)? modalList.size() : 0;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, desc, status, t_timer, time_lft;
        CountDownTimer countDownTimer;
        private ImageView remove,play, pause;
        private LinearLayout timeer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.t_title);
            desc = itemView.findViewById(R.id.t_desc);
            status = itemView.findViewById(R.id.t_status);
            t_timer = itemView.findViewById(R.id.t_timer);

            remove = itemView.findViewById(R.id.remove);
            play = itemView.findViewById(R.id.play);
            pause = itemView.findViewById(R.id.pause);
            timeer = itemView.findViewById(R.id.timeer);
            time_lft = itemView.findViewById(R.id.time_lft);
        }
    }
}
