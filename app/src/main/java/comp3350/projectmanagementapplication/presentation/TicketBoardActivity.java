package comp3350.projectmanagementapplication.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import comp3350.projectmanagementapplication.databinding.ActivityTicketBoardBinding;
import comp3350.projectmanagementapplication.objects.Status;

public class TicketBoardActivity extends FragmentActivity {

    private static final Status[] LANES = Status.values();

    private ActivityTicketBoardBinding binding;
    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityTicketBoardBinding.inflate(this.getLayoutInflater());
        this.args = this.getIntent().getExtras();

        this.loadTickets();

        this.setContentView(this.binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.loadTickets();
    }

    public void openNewTicket(View view){
        Intent intent = new Intent(this, NewTicketActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtras(this.args);
        startActivity(intent);
    }

    private void loadTickets() {
        this.binding.lanePager.setAdapter(
                new PagerAdapter(this, this.args)
        );
    }

    private static class PagerAdapter extends FragmentStateAdapter {
        private final Bundle args;

        public PagerAdapter(FragmentActivity fragmentActivity, Bundle args) {
            super(fragmentActivity);

            this.args = args;
        }

        @Override
        public Fragment createFragment(int position) {
            TicketLaneFragment fragment = new TicketLaneFragment(LANES[position].getValue(), this.args);

            fragment.setArguments(this.args);

            return fragment;
        }

        @Override
        public int getItemCount() {
            return LANES.length;
        }
    }

    public void openProjectStat(View view){
        Intent intent = new Intent(this, ProjectStatActivity.class);

        intent.putExtras(args);

        startActivity(intent);
    }
}