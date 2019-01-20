package com.example.zenitka.taskmanager.Team_;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zenitka.taskmanager.R;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder>{

    public final static int ACTION_CLICK = 1;

    private List<Team> teams;
    private ItemClickListener ClickListener;

    TeamAdapter(List<Team> teams) {
        this.teams = teams;
    }

    void UpdateTeam(int position, Team team) {
        teams.set(position, team);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.team_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder holder, final int position) {
        final Team team = teams.get(position);
        holder.name.setText(team.name);
    }

    @Override
    public int getItemCount() {
        if(teams != null)
            return teams.size();
        else return 0;
    }

    public Team getTeam(int position){
        return teams.get(position);
    }

    void setTeams(List<Team> nteams) {
        teams = nteams;
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
