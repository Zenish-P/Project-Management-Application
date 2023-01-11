package comp3350.projectmanagementapplication.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.objects.Team;
import comp3350.projectmanagementapplication.presentation.ProjectSelectActivity;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {
    private final ArrayList<Team> teams;

    public TeamListAdapter(ArrayList<Team> teams) {
        this.teams = teams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.team_list_entry, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Team team = this.teams.get(position);
        String teamName = team.getName();

        viewHolder.getTextView().setText(teamName);
        viewHolder.getTextView().setOnClickListener(view -> {
            Context ctx = view.getContext();
            Intent intent = new Intent(ctx, ProjectSelectActivity.class);

            // Setup args to send to activity
            Bundle bundle = new Bundle();
            bundle.putString("teamName", teamName);
            intent.putExtras(bundle);

            // Navigate to the list of projects
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.teams.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);

            this.textView = view.findViewById(R.id.teamName);
        }

        public TextView getTextView() {
            return this.textView;
        }
    }
}
