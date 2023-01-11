package comp3350.projectmanagementapplication.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.presentation.NewUserActivity;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private final ArrayList<User> users;
    private String teamName;

    public UserListAdapter(ArrayList<User> users, String team) {
        this.users = users;
        this.teamName = team;
    }

    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_list_entry, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        User user = this.users.get(position);
        String userName = user.getName();
        String userBio = user.getBio();

        viewHolder.getNameView().setText(userName);
        viewHolder.getBioView().setText(userBio);
        viewHolder.getNameView().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Context ctx = view.getContext();
                Intent intent = new Intent(ctx, NewUserActivity.class);
                intent.putExtra("type", "edit");
                intent.putExtra("teamName", teamName);
                // Setup args to send to activity
                Bundle bundle = new Bundle();
                bundle.putString("name", userName);
                bundle.putString("bio", userBio);
                intent.putExtras(bundle);

                // Navigate to the list of projects
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView bioView;

        public ViewHolder(View view) {
            super(view);

            this.nameView = view.findViewById(R.id.userName);
            this.bioView = view.findViewById(R.id.userBio);
        }

        public TextView getNameView() {
            return this.nameView;
        }
        public TextView getBioView() { return this.bioView; }
    }
}
