package comp3350.projectmanagementapplication.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.databinding.ActivityTeamSelectBinding;
import comp3350.projectmanagementapplication.presentation.adapters.TeamListAdapter;

public class TeamSelectActivity extends AppCompatActivity {
    private ActivityTeamSelectBinding binding;
    private TeamListAdapter teamListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_select);
        this.binding = ActivityTeamSelectBinding.inflate(this.getLayoutInflater());

        this.loadTeams();

        this.setContentView(this.binding.getRoot());
        this.setSupportActionBar(this.binding.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.loadTeams();
    }

    public void openCreatePage(View view) {
        Intent intent = new Intent(this, TeamCreateActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    private void loadTeams() {
        RecyclerView teamList = this.binding.teamList;
        teamList.setLayoutManager(new LinearLayoutManager(this));
        this.teamListAdapter = new TeamListAdapter(
                new DatabaseAccessor().getTeams()
        );
        teamList.setAdapter(this.teamListAdapter);
    }
}