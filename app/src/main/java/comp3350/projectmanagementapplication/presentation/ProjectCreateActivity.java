package comp3350.projectmanagementapplication.presentation;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.objects.Project;

public class ProjectCreateActivity extends AppCompatActivity {
    EditText projectNameInput;
    EditText projectDescriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_create);

        this.projectNameInput = this.findViewById(R.id.projectNameInput);
        this.projectDescriptionInput = this.findViewById(R.id.projectDescriptionInput);
        Button finishButtonInProject = this.findViewById(R.id.finishButtonInProject);
        finishButtonInProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProject();
            }
        });
    }

    private void createProject(){
        Editable nameField = this.projectNameInput.getText();
        Editable descriptionField = this.projectDescriptionInput.getText();
        String projectName = nameField.toString();
        String projectDescription = descriptionField.toString();

        Bundle args = this.getIntent().getExtras();
        String teamName = args.getString("teamName");
        boolean projectAdded = new DatabaseAccessor().addProject(teamName,new Project(projectName, projectDescription,null));

        if (projectName.isEmpty()) {
            this.projectNameInput.setError("Project must have a name");
        } else {
            if (projectAdded) {
                this.finish();
            } else {
                this.projectNameInput.setError(String.format("A project with name '%s' already exists", projectName));
            }
        }
    }
}