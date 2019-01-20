package com.example.zenitka.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    public final static int ACTION_CLICK = 1;

    private List<Task> tasks;
    private ItemClickListener ClickListener;

    TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    void UpdateTask(int position, Task task) {
        tasks.set(position, task);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, final int position) {
        final Task task = tasks.get(position);
        holder.name.setText(task.name);
        holder.date.setText(task.date);
    }

    @Override
    public int getItemCount() {
        if(tasks != null)
            return tasks.size();
        else return 0;
    }

    public  Task getTask(int position){
        return  tasks.get(position);
    }

    void setTasks(List<Task> ntasks) {
        tasks = ntasks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView date;
        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
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
