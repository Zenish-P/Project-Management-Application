package comp3350.projectmanagementapplication.business;

import junit.framework.TestCase;

import org.junit.Before;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;

import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Priority;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.objects.UserFeatureTicket;

public class OperationsTest extends TestCase {

    ArrayList<User> users;
    ArrayList<FeatureTicket> tickets;
    ArrayList<UserFeatureTicket> userTickets;
    Calendar today;
    Calendar weekFromNow;
    Calendar lastWeek;
    Operations operations;

    @Before
    public void setUp() {
        operations = new Operations();

        users = new ArrayList<>();
        users.add(new User("foo", ""));
        users.add(new User("bar", ""));
        users.add(new User("baz", ""));

        tickets = new ArrayList<>();
        userTickets = new ArrayList<>();

        today = Calendar.getInstance();
        weekFromNow = Calendar.getInstance();
        weekFromNow.add(Calendar.DAY_OF_MONTH, 7);
        lastWeek = Calendar.getInstance();
        lastWeek.add(Calendar.DAY_OF_MONTH, -7);
    }

    public void testAllTicketsAssigned() {
        standardTickets();

        ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(4, tickets, userTickets);

        assertEquals(newTickets.size(), 4);
        assertEquals("t3", newTickets.get(0).getName());
        assertEquals("t1", newTickets.get(1).getName());
        assertEquals("t4", newTickets.get(2).getName());
        assertEquals("t2", newTickets.get(3).getName());
    }

    public void testMixedTickets() {
        standardTickets();

        Calendar testDate = Calendar.getInstance();
        testDate.add(Calendar.DAY_OF_MONTH, 3);

        tickets.add(new FeatureTicket("t5", "", Priority.High, 2, null, testDate, today));

        ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(3, tickets, userTickets);
        assertEquals("t3", newTickets.get(0).getName());
        assertEquals("t1", newTickets.get(1).getName());
        assertEquals("t5", newTickets.get(2).getName());
    }

    public void testOnlyUnassignedTickets() {
        Calendar t1Date = Calendar.getInstance();
        t1Date.add(Calendar.DAY_OF_MONTH, 5);
        Calendar t2Date = Calendar.getInstance();
        t2Date.add(Calendar.DAY_OF_MONTH, 10);
        Calendar t3Date = Calendar.getInstance();
        t3Date.add(Calendar.DAY_OF_MONTH, 6);
        Calendar t4Date = Calendar.getInstance();
        t4Date.add(Calendar.DAY_OF_MONTH, 3);
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        tickets.add(new FeatureTicket("t1", "", Priority.High, 2, null, t1Date, today));
        tickets.add(new FeatureTicket("t2", "", Priority.Medium, 1, null, t2Date, today));
        tickets.add(new FeatureTicket("t3", "", Priority.High, 3, null, t3Date, today));
        tickets.add(new FeatureTicket("t4", "", Priority.Low, 1, null, t4Date, yesterday));

        ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(4, tickets, userTickets);
        assertEquals(newTickets.size(), 4);
        assertEquals("t1", newTickets.get(0).getName());
        assertEquals("t3", newTickets.get(1).getName());
        assertEquals("t2", newTickets.get(2).getName());
        assertEquals("t4", newTickets.get(3).getName());
    }


    public void testNumTicketsSizeZero() {
        standardTickets();

        ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(0, tickets, userTickets);

        assertEquals(newTickets.size(), 0);
    }

    public void testTicketsSizeZero() {
        ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(0, new ArrayList<>(), userTickets);

        assertEquals(newTickets.size(), 0);
    }

    public void testNullInput() {
        ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(3, null, userTickets);

        assertEquals(new ArrayList<>(), newTickets);
    }

    public void testNumTicketsGreaterThanFeatureTicketPoolSize() {
        standardTickets();
        ArrayList<FeatureTicket> newTickets = null;
        try {
            newTickets = operations.getNewUserTickets(5, tickets, userTickets);
            fail();
        } catch (InvalidParameterException e) {
        }
    }

    public void testReturnOverdueTickets() {
        standardTickets();

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        Calendar twoDaysAgo = Calendar.getInstance();
        twoDaysAgo.add(Calendar.DAY_OF_MONTH, -2);

        tickets.add(new FeatureTicket("t5", "", Priority.Medium, 1, null, yesterday, lastWeek));
        tickets.add(new FeatureTicket("t6", "", Priority.High, 1, null, twoDaysAgo, lastWeek));

        ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(2, tickets, userTickets);

        assertEquals("t6", newTickets.get(0).getName());
        assertEquals("t5", newTickets.get(1).getName());
    }

    public void testReturnOverdueAndStandardTickets() {
        standardTickets();

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        Calendar twoDaysAgo = Calendar.getInstance();
        twoDaysAgo.add(Calendar.DAY_OF_MONTH, -2);

        tickets.add(new FeatureTicket("t5", "", Priority.Medium, 1, null, yesterday, lastWeek));
        tickets.add(new FeatureTicket("t6", "", Priority.High, 1, null, twoDaysAgo, lastWeek));

        ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(3, tickets, userTickets);

        assertEquals("t6", newTickets.get(0).getName());
        assertEquals("t5", newTickets.get(1).getName());
        assertEquals("t3", newTickets.get(2).getName());
    }

    public void testNullPriority() {
        standardTickets();

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);

        tickets.add(new FeatureTicket("t5", "", null, 1, null, tomorrow, today));
        tickets.get(4).setAssignee(users.get(0));
        userTickets.add(new UserFeatureTicket(users.get(0), tickets.get(4)));


        try {
            operations.getNewUserTickets(3, tickets, userTickets);
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testNegativeNumTickets() {
        try {
            ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(-2, tickets, userTickets);
            fail();
        } catch (InvalidParameterException e) {
        }
    }

    public void testNullDates() {
        tickets.add(new FeatureTicket("t1", "", Priority.High, 2, null, null));
        try {
            ArrayList<FeatureTicket> newTickets = operations.getNewUserTickets(1, tickets, userTickets);
            fail();
        } catch (NullPointerException e) {
        }
    }

    private void standardTickets() {
        Calendar t1Date = Calendar.getInstance();
        t1Date.add(Calendar.DAY_OF_MONTH, 5);
        Calendar t2Date = Calendar.getInstance();
        t2Date.add(Calendar.DAY_OF_MONTH, 10);
        Calendar t3Date = Calendar.getInstance();
        t3Date.add(Calendar.DAY_OF_MONTH, 6);
        Calendar t4Date = Calendar.getInstance();
        t4Date.add(Calendar.DAY_OF_MONTH, 3);
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        tickets.add(new FeatureTicket("t1", "", Priority.High, 2, null, t1Date, today));
        tickets.get(0).setAssignee(users.get(1));
        userTickets.add(new UserFeatureTicket(users.get(1), tickets.get(0)));

        tickets.add(new FeatureTicket("t2", "", Priority.Low, 1, null, t2Date, today));
        tickets.get(1).setAssignee(users.get(2));
        userTickets.add(new UserFeatureTicket(users.get(2), tickets.get(1)));

        tickets.add(new FeatureTicket("t3", "", Priority.High, 3, null, t3Date, today));
        tickets.get(2).setAssignee(users.get(0));
        userTickets.add(new UserFeatureTicket(users.get(0), tickets.get(2)));

        tickets.add(new FeatureTicket("t4", "", Priority.Low, 1, null, t4Date, yesterday));
        tickets.get(3).setAssignee(users.get(0));
        userTickets.add(new UserFeatureTicket(users.get(0), tickets.get(2)));

    }
}
