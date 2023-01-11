package comp3350.projectmanagementapplication.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.databinding.ActivityProjectSelectBinding;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.presentation.adapters.ProjectListAdapter;

public class ProjectSelectActivity extends AppCompatActivity {
    private ActivityProjectSelectBinding binding;
    private Bundle args;
    private String teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_select);

        // Read args from intent
        this.args = this.getIntent().getExtras();
        teamName = this.args.getString("teamName");
        // Get the projects for the team
        this.binding = ActivityProjectSelectBinding.inflate(this.getLayoutInflater());

        // Set the toolbar title
        this.binding.toolbar.setTitle(teamName + "'s Projects");

        this.loadProjects();

        this.setContentView(this.binding.getRoot());
        this.setSupportActionBar(this.binding.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.loadProjects();
    }

    public void openCreateProject(View view){
        Intent intent = new Intent(this,ProjectCreateActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtras(this.args);
        startActivity(intent);
    }

    public void viewUsers(View view) {
        Intent intent = new Intent(this, UserSelectActivity.class);
        intent.putExtra("teamName", teamName);
        startActivity(intent);
    }

    private void loadProjects() {
        ArrayList<Project> projects = new DatabaseAccessor().getProjects(teamName);
        RecyclerView projectList = this.binding.projectList;
        projectList.setLayoutManager(new LinearLayoutManager(this));
        projectList.setAdapter(new ProjectListAdapter(args, projects));
    }
}