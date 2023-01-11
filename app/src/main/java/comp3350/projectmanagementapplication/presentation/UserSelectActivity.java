package comp3350.projectmanagementapplication.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.databinding.ActivityUserSelectBinding;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.presentation.adapters.UserListAdapter;

public class UserSelectActivity extends AppCompatActivity  {
    private ActivityUserSelectBinding binding;
    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = this.getIntent().getExtras();

        this.binding = ActivityUserSelectBinding.inflate(this.getLayoutInflater());
        this.binding.toolbar.setTitle("List of Users");

        this.loadUsers();

        this.setContentView(this.binding.getRoot());
        this.setSupportActionBar(this.binding.toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.loadUsers();
    }

    public void openNewUser(View view){
        Intent intent = new Intent(this, NewUserActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("type", "create");
        intent.putExtra("teamName", args.get("teamName").toString());
        startActivity(intent);
    }

    private void loadUsers() {
        ArrayList<User> users = new DatabaseAccessor().getUsers(args.get("teamName").toString());
        RecyclerView userList = this.binding.userList;
        userList.setLayoutManager(new LinearLayoutManager(this));
        userList.setAdapter(new UserListAdapter(users, args.get("teamName").toString()));
    }
}
