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
import java.util.Calendar;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.presentation.TicketEditActivity;

public class FeatureTicketListAdapter extends RecyclerView.Adapter<FeatureTicketListAdapter.ViewHolder> {
    private final ArrayList<FeatureTicket> featureTickets;
    private String projectName;
    private String teamName;

    public FeatureTicketListAdapter(ArrayList<FeatureTicket> featureTickets, String projectName, String teamName) {
        this.featureTickets = featureTickets;
        this.projectName = projectName;
        this.teamName = teamName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ticket_lane_entry, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        FeatureTicket featureTicket = this.featureTickets.get(position);
        viewHolder.getTicketName().setText(featureTicket.getName());
        viewHolder.getTicketDescription().setText(featureTicket.getDescription());
        viewHolder.getTicketPriority().setText(String.valueOf(featureTicket.getPriority()));
        viewHolder.getTicketCost().setText(String.valueOf(featureTicket.getCost()));

        viewHolder.getTicketDescription().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEdit(view, featureTicket);
            }
        });

        viewHolder.getTicketName().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEdit(view, featureTicket);
            }
        });
    }

    public void toEdit(View view, FeatureTicket featureTicket) {
        Context ctx = view.getContext();
        Intent intent = new Intent(ctx, TicketEditActivity.class);

        // Setup args to send to activity
        Bundle args = new Bundle();
        args.putString("ticketName", featureTicket.getName());
        args.putString("ticketDescription", featureTicket.getDescription());
        args.putInt("ticketCost", featureTicket.getCost());
        args.putString("ticketPriority", featureTicket.getPriority().toString());
        args.putInt("ticketYearDeadline", featureTicket.getDeadline().get(Calendar.YEAR));
        args.putInt("ticketMonthDeadline", featureTicket.getDeadline().get(Calendar.MONTH));
        args.putInt("ticketDayDeadline", featureTicket.getDeadline().get(Calendar.DAY_OF_MONTH));
        args.putString("currProjectName", projectName);
        args.putString("currTeamName", teamName);
        intent.putExtras(args);
        ctx.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return this.featureTickets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView ticketName;
        private final TextView ticketDescription;
        private final TextView ticketPriority;
        private final TextView ticketCost;

        public ViewHolder(View view) {
            super(view);

            this.ticketName = view.findViewById(R.id.ticketName);
            this.ticketDescription = view.findViewById(R.id.ticketDescription);
            this.ticketPriority = view.findViewById(R.id.ticketPriority);
            this.ticketCost = view.findViewById(R.id.ticketCost);
        }

        public TextView getTicketName() {
            return this.ticketName;
        }

        public TextView getTicketDescription() {
            return this.ticketDescription;
        }

        public TextView getTicketPriority() {
            return this.ticketPriority;
        }

        public TextView getTicketCost() {
            return this.ticketCost;
        }
    }
}
