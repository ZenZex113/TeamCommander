package com.example.zenitka.taskmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zenitka.taskmanager.net.Code;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.Network;
import com.example.zenitka.taskmanager.net.TokenProject;
import com.example.zenitka.taskmanager.net.TokenTeam;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zenitka.taskmanager.RegLog.LoginActivity.SAVED_TOKEN;
import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{

    SharedPreferences mSharedPreferences;

    HelloApi api = Network.getInstance().getApi();

    @SuppressLint("CheckResult")
    public void deleteProject(TokenProject tokenProject) {
        api.deleteProject(tokenProject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Code>() {
                    @Override
                    public void accept(Code code) throws Exception {
                        if (code.getCode() == CODE_OK) {
                            ;;
                        } else {
                            System.err.println(code.getCode());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println(throwable.getMessage());
                    }
                });
    }

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
        context = parent.getContext();
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

    private Context context;

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

                    mSharedPreferences = context.getSharedPreferences("com.example.zenitka.taskmanager.token", MODE_PRIVATE);

                    deleteProject(new TokenProject(mSharedPreferences.getString(SAVED_TOKEN, "No token"), project.id));

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
