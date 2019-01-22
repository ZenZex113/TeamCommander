package com.example.zenitka.taskmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zenitka.taskmanager.net.Code;
import com.example.zenitka.taskmanager.net.CodeListTeam;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.Network;
import com.example.zenitka.taskmanager.net.TokenTeam;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zenitka.taskmanager.RegLog.LoginActivity.SAVED_TOKEN;
import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;
import static com.example.zenitka.taskmanager.helpers.Errors.ERRORS;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder>{

    SharedPreferences mSharedPreferences;

    HelloApi api = Network.getInstance().getApi();

    @SuppressLint("CheckResult")
    public void deleteUserFromTeam(TokenTeam tokenTeam) {
        api.deleteUserFromTeam(tokenTeam)
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

    private List<Team> teams;
    private ItemClickListener ClickListener;
    public TeamViewModel mTeamViewModel;

    private Context context;

    TeamAdapter(List<Team> teams) {
        this.teams = teams;
    }

    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
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
        ImageButton delete;
        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Team team = teams.get(position);

                    mSharedPreferences = context.getSharedPreferences("com.example.zenitka.taskmanager.token", MODE_PRIVATE);
//                    loadTeamsList((mSharedPreferences.getString(SAVED_TOKEN, "No token")));

                    deleteUserFromTeam(new TokenTeam(mSharedPreferences.getString(SAVED_TOKEN, "No token"), team.id));

                    mTeamViewModel.delete(team);
                    teams.remove(position);
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

    void setClickListener(ItemClickListener itemClickListener) {
        this.ClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int action, int position);
    }
}