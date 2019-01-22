package com.example.zenitka.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TeamTaskAdapter extends RecyclerView.Adapter<TeamTaskAdapter.ViewHolder>{

    public final static int ACTION_CLICK = 1;

    private List<TeamTask> ttasks;
    private ItemClickListener ClickListener;

    public TeamTaskViewModel mTeamTaskViewModel;

    TeamTaskAdapter(List<TeamTask> ttasks) {
        this.ttasks = ttasks;
    }

    @NonNull
    @Override
    public TeamTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.team_task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamTaskAdapter.ViewHolder holder, final int position) {
        final TeamTask teamTask = ttasks.get(position);
        holder.name.setText(teamTask.tname);
        holder.date.setText(teamTask.tdate);
        holder.worker.setText(teamTask.worker);
        switch (teamTask.tstatus){
            case 1:
                holder.status.setImageResource(R.drawable.ic_close_black_24dp);
                holder.status_name.setText("Не начато");
                break;
            case 2:
                holder.status.setImageResource(R.drawable.ic_build_black_24dp);
                holder.status_name.setText("В процессе");
                break;
            case 3:
                holder.status.setImageResource(R.drawable.ic_check_black_24dp);
                holder.status_name.setText("Завершено");
                break;
        }

        switch (teamTask.tpriority){
            case 3:
                holder.priority.setImageResource(R.drawable.ic_looks_3_black_24dp);
                holder.priority_name.setText("Низкий");
                break;
            case 2:
                holder.priority.setImageResource(R.drawable.ic_looks_two_black_24dp);
                holder.priority_name.setText("Средний");
                break;
            case 1:
                holder.priority.setImageResource(R.drawable.ic_looks_one_black_24dp);
                holder.priority_name.setText("Высокий");
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(ttasks != null)
            return ttasks.size();
        else return 0;
    }

    public TeamTask getTeamTask(int position){
        return ttasks.get(position);
    }

    void setTeamTasks(List<TeamTask> nttasks) {
        ttasks = nttasks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView date;
        ImageButton delete;
        TextView worker;
        TextView status_name, priority_name;
        ImageView status, priority;
        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            worker = itemView.findViewById(R.id.worker);
            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    TeamTask ttask = ttasks.get(position);
                    mTeamTaskViewModel.delete(ttask);
                    ttasks.remove(position);
                    notifyItemRemoved(position);
                }
            });
            itemView.setOnClickListener(this);
            status = itemView.findViewById(R.id.status_i);
            priority = itemView.findViewById(R.id.priority_i);
            status_name = itemView.findViewById(R.id.status_name);
            priority_name = itemView.findViewById(R.id.priority_name);
        }
        @Override
        public void onClick(View view) {
            if (ClickListener != null)
                ClickListener.onItemClick(ACTION_CLICK, getAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.ClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int action, int position);
    }
}
/*
package ru.pap.archapp.ui.adapters;

public interface IAdapterClickListener {

    void onActionClick(int action, int position);

}*/
