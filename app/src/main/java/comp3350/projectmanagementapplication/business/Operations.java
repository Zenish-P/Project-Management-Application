package comp3350.projectmanagementapplication.business;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.objects.UserFeatureTicket;

public class Operations {
    /*
    getNewUserTickets

    getNewUserTickets is a method to return an arrayList of featureTickets of size numTickets
    where the array is the "highest priority" tickets in the featureTicketPool arrayList. The
    method of determining "highest priority" tickets is by using a formula that takes into
    account the time left to do each ticket, whether the ticket is assigned or not, and if
    assigned how busy the user is.
    */
    public ArrayList<FeatureTicket> getNewUserTickets(int numTickets, ArrayList<FeatureTicket> featureTicketPool, ArrayList<UserFeatureTicket> userFeatureTickets)
            throws InvalidParameterException, NullPointerException, IllegalStateException {
        ArrayList<FeatureTicket> newUserTickets = new ArrayList<>();

        // hash map which stores the ticket weight and the actual ticket
        ArrayList<FeatureTicket> tickets;
        ArrayList<FeatureTicket> sortedTickets = new ArrayList<>();
        ArrayList<FeatureTicket> sortedOverdueTickets = new ArrayList<>();

        if (this.verifyInput(numTickets, featureTicketPool)) {
            tickets = (ArrayList<FeatureTicket>) featureTicketPool.clone();
            Collections.sort(tickets, new Comparator<FeatureTicket>() {
                @Override
                public int compare(FeatureTicket ticket1, FeatureTicket ticket2) {
                    return ((Float) (getUserWeight(ticket2.getAssignee(), userFeatureTickets) * ticket2.getPriority().getValue()))
                            .compareTo(getUserWeight(ticket1.getAssignee(), userFeatureTickets) * ticket1.getPriority().getValue());
                }
            });

            // go through each FeatureTicket
            for (FeatureTicket featureTicket : tickets) {
                // ticketWeight = UserWeight * ticket-priority
                if (featureTicket.tillDue() == -1) {
                    sortedOverdueTickets.add(featureTicket);
                } else {
                    sortedTickets.add(featureTicket);
                }
            }
        } else {
            numTickets = -1;
        }

        if (sortedOverdueTickets.size() > 0) {
            for (int i = 0; i < numTickets && i < sortedOverdueTickets.size(); i++) {
                newUserTickets.add(sortedOverdueTickets.get(i));
            }

            numTickets = numTickets - sortedOverdueTickets.size();
        }

        for (int i = 0; i < numTickets; i++) {
            newUserTickets.add(sortedTickets.get(i));
        }

        return newUserTickets;
    }

    /*
    getUserWeight

    For a user, and a set of UserFeatureTickets it finds the "weight" of said user. The user weight
    is a metric of showing how busy a particular user is. It is vaguely based around 1 is if the user
    has all of their time allocated to tickets. Over 1 means that they have they dont have
    enough time to finish all their tickets, less than 1 means that they probably can finish their
    tickets and have time left over.
     */
    private float getUserWeight(User user, ArrayList<UserFeatureTicket> userFeatureTickets) {
        float output = 1;

        if (user != null) {
            ArrayList<FeatureTicket> elements = sortTicketsByUser(user, userFeatureTickets);

            for (FeatureTicket e : elements) {
                // UserWeight = sum (1/(ticket-till-due) * ticket-cost)
                // where the sum iterates through all tickets
                output += (1 / (float) e.tillDue()) * e.getCost();
            }
        }

        return output;
    }

    /*
    verifyInput

    helper method for getNewUserTickets that checks the input parameters of that method for invalid parameters.
     */
    private boolean verifyInput(int numTickets, ArrayList<FeatureTicket> featureTickets) throws InvalidParameterException {
        boolean inputIsSafe = featureTickets != null;

        if (inputIsSafe) {
            if (numTickets < 0) {
                throw new InvalidParameterException("Cannot return less than 0 tickets");
            } else if (featureTickets.size() < numTickets) {
                throw new InvalidParameterException("There are less than " + numTickets + " tickets in existence");
            }
        }

        return inputIsSafe;
    }

    private ArrayList<FeatureTicket> sortTicketsByUser(User user, ArrayList<UserFeatureTicket> relationships) {
        ArrayList<FeatureTicket> userTickets = new ArrayList<>();

        for (UserFeatureTicket userFeatureTicket : relationships) {
            if (userFeatureTicket.getUser().getName().equals(user.getName())) {
                userTickets.add(userFeatureTicket.getFeatureTicket());
            }
        }

        return userTickets;
    }
}
