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
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.presentation.TicketBoardActivity;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private final Bundle args;
    private final ArrayList<Project> projects;

    public ProjectListAdapter(Bundle args, ArrayList<Project> projects) {
        this.args = args;
        this.projects = projects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.project_list_entry, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Bundle args = this.args;
        Project project = this.projects.get(position);

        String projectName = project.getName();

        viewHolder.getTextView().setText(projectName);
        viewHolder.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context ctx = view.getContext();
                Intent intent = new Intent(ctx, TicketBoardActivity.class);

                // Setup args to send to activity
                args.putString("projectName", projectName);
                intent.putExtras(args);

                // Navigate to the list of projects
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.projects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);

            this.textView = view.findViewById(R.id.projectName);
        }

        public TextView getTextView() {
            return this.textView;
        }
    }
}
