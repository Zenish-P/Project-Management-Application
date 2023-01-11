package comp3350.projectmanagementapplication.presentation;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.objects.Team;

public class TeamCreateActivity extends AppCompatActivity {
    EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_team_create);

        this.nameInput = this.findViewById(R.id.nameInput);
        Button finishButton = this.findViewById(R.id.finishButton);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTeam();
            }
        });
    }

    private void createTeam() {
        Editable nameField = this.nameInput.getText();
        String teamName = nameField.toString();
        boolean teamAdded = new DatabaseAccessor().addTeam(new Team(teamName));

        if (teamName.isEmpty()) {
            this.nameInput.setError("Team must have a name");
        } else {
            if (teamAdded) {
                this.finish();
            } else {
                this.nameInput.setError(String.format("A team with the name '%s' already exists", teamName));
            }
        }
    }
}
