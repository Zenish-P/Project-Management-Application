package comp3350.projectmanagementapplication.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.databinding.ActivityUserCreationBinding;
import comp3350.projectmanagementapplication.objects.User;

public class NewUserActivity extends AppCompatActivity {
    private ActivityUserCreationBinding binding;
    private EditText nameInput, bioInput;
    private Bundle args;
    private DatabaseAccessor databaseAccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseAccessor = new DatabaseAccessor();

        args = this.getIntent().getExtras();

        this.binding = ActivityUserCreationBinding.inflate(this.getLayoutInflater());

        if(args.get("type").equals("create")){
            setUpCreate();
        }else if(args.get("type").equals("edit")){
            setUpEdit();
        }
    }

    private void setUpCreate(){
        // Set the toolbar title
        setContentView(R.layout.activity_user_creation);
        this.binding.toolbar.setTitle("Create New Ticket");

        Button button = findViewById(R.id.buttonOne);
        button.setText(getString(R.string.save));
        button = findViewById(R.id.buttonTwo);
        button.setText(getString(R.string.cancel));

        nameInput = findViewById(R.id.editName);
        bioInput = findViewById(R.id.editBio);
    }

    private void setUpEdit(){
        // Set the toolbar title
        setContentView(R.layout.activity_user_creation);
        this.binding.toolbar.setTitle("Edit Ticket");

        Button button = findViewById(R.id.buttonOne);
        button.setText(getString(R.string.save_changes));
        button = findViewById(R.id.buttonTwo);
        button.setText(getString(R.string.delete));

        nameInput = findViewById(R.id.editName);
        nameInput.setText(args.getString("name"));
        bioInput = findViewById(R.id.editBio);
        bioInput.setText(args.getString("bio"));
    }

    // cancel or delete
    public void buttonTwo(View view) {
        boolean result = true;
        if(args.get("type").equals("edit")) {
            result = databaseAccessor.removeUser(args.getString("teamName"), args.getString("name"));
        }
        if(result) {
            this.finish();
        } else {
            this.nameInput.setError(String.format("No user with the name '%s' exists", nameInput.getText().toString()));
        }
    }

    // save changes or save new user
    public void buttonOne(View view) {
        if (args.get("type").equals("edit")) {
            databaseAccessor.updateUser(args.getString("teamName"), args.getString("name"), new User(nameInput.getText().toString(), bioInput.getText().toString()));
        } else if (args.get("type").equals("create")) {
            String name = nameInput.getText().toString();

            if (name.isEmpty()) {
                this.nameInput.setError("User must have a name");
                return;
            } else if (!databaseAccessor.addUser(args.getString("teamName"), new User(name, bioInput.getText().toString()))) {
                this.nameInput.setError(String.format("A user with the name '%s' already exists", name));
                return;
            }
        }

        this.finish();
    }
}
