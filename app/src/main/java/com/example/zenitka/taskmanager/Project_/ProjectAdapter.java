package com.example.zenitka.taskmanager.Project_;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zenitka.taskmanager.R;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{

    public final static int ACTION_CLICK = 1;

    private List<Project> projects;
    private ProjectAdapter.ItemClickListener ClickListener;

    ProjectAdapter(List<Project> projects) {
        this.projects = projects;
    }

    void UpdateProject(int position, Project project) {
        projects.set(position, project);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.project_list_item, parent, false);
        return new ProjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.ViewHolder holder, final int position) {
        final Project project = projects.get(position);
        holder.name.setText(project.name);
        holder.date.setText(project.date);
    }

    @Override
    public int getItemCount() {
        if(projects != null)
            return projects.size();
        else return 0;
    }

    public Project getProject(int position){
        return projects.get(position);
    }

    void setProjects(List<Project> nprojects) {
        projects = nprojects;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView date;
        ViewHolder(final View itemView){
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

    void setClickListener(ProjectAdapter.ItemClickListener itemClickListener) {
        this.ClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int action, int position);
    }
}
