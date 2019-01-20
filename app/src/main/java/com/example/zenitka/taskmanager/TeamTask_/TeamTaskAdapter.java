package com.example.zenitka.taskmanager.TeamTask_;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zenitka.taskmanager.R;

import java.util.List;

public class TeamTaskAdapter extends RecyclerView.Adapter<TeamTaskAdapter.ViewHolder>{

    public final static int ACTION_CLICK = 1;

    private List<TeamTask> team_tasks;
    private ItemClickListener ClickListener;

    TeamTaskAdapter(List<TeamTask> team_tasks) {
        this.team_tasks = team_tasks;
    }

    void UpdateTeamTask(int position, TeamTask teamTask) {
        team_tasks.set(position, teamTask);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public TeamTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.team_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamTaskAdapter.ViewHolder holder, final int position) {
        final TeamTask teamTask = team_tasks.get(position);
        holder.name.setText(teamTask.name);
    }

    @Override
    public int getItemCount() {
        if(team_tasks != null)
            return team_tasks.size();
        else return 0;
    }

    public TeamTask getTeamTask(int position){
        return team_tasks.get(position);
    }

    void setTeamTasks(List<TeamTask> ttasks) {
        team_tasks = ttasks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
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
