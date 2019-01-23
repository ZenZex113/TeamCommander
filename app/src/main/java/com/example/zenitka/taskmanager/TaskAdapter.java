package com.example.zenitka.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    public final static int ACTION_CLICK = 1;

    private List<Task> tasks;
    private ItemClickListener ClickListener;
    public TaskViewModel mTaskViewModel;

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
        switch (task.status){
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

        switch (task.priority){
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
        TextView status_name, priority_name;
        ImageButton delete;
        CardView cv;
        ImageView status, priority;
        ViewHolder(final View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            cv = itemView.findViewById(R.id.cv);
            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Task task = tasks.get(position);
                    mTaskViewModel.delete(task);
                    tasks.remove(position);
                    notifyItemRemoved(position);
                }
            });
            status = itemView.findViewById(R.id.status_i);
            priority = itemView.findViewById(R.id.priority_i);
            status_name = itemView.findViewById(R.id.status_name);
            priority_name = itemView.findViewById(R.id.priority_name);
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

