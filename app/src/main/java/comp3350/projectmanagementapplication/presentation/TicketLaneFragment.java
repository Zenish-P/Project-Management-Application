package comp3350.projectmanagementapplication.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.presentation.adapters.FeatureTicketListAdapter;

public class TicketLaneFragment extends Fragment {
    private final String title;
    private final Bundle args;

    public TicketLaneFragment(String title, Bundle args) {
        this.title = title;
        this.args = args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_lane, container, false);

        ArrayList<FeatureTicket> featureTickets = new DatabaseAccessor().getFeatureTickets(
                this.args.getString("teamName"),
                this.args.getString("projectName")
        );
        ArrayList<FeatureTicket> filteredFeatureTickets = new ArrayList<>();

        for (FeatureTicket featureTicket : featureTickets) {
            if (featureTicket.getStatus().getValue().equals(this.title)) {
                filteredFeatureTickets.add(featureTicket);
            }
        }

        TextView laneTitle = view.findViewById(R.id.title);
        laneTitle.setText(this.title);
        RecyclerView ticketList = view.findViewById(R.id.ticketList);
        ticketList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ticketList.setAdapter(new FeatureTicketListAdapter(filteredFeatureTickets, this.args.getString("projectName"), this.args.getString("teamName")));

        return view;
    }
}