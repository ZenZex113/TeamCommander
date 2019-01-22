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

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{

    public final static int ACTION_CLICK = 1;

    private List<Project> projects;
    private ProjectAdapter.ItemClickListener ClickListener;
    public ProjectViewModel mProjectViewModel;

    ProjectAdapter(List<Project> projects) {
        this.projects = projects;
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
        ImageButton delete;
        CardView cv;
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
                    Project project = projects.get(position);
                    mProjectViewModel.delete(project);
                    projects.remove(position);
                    notifyItemRemoved(position);
                }
            });
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
