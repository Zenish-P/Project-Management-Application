package comp3350.projectmanagementapplication.objects;

import junit.framework.TestCase;

import java.util.Calendar;

public class TicketTest extends TestCase {

    public void testDatesSame() {
        Calendar date = Calendar.getInstance();
        FeatureTicket ft = new FeatureTicket("ticket", "this is a ticket",
                Priority.Medium, 0, null, date, date);

        assertEquals(0, ft.getWorkableTime());
        assertEquals(0, ft.tillDue());
    }

    public void testDeadlineMoreThanCost() {
        Calendar dateStart = Calendar.getInstance();
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DAY_OF_MONTH, 3);
        deadline.add(Calendar.MINUTE, 1);
        FeatureTicket ft = new FeatureTicket("ticket", "this is a ticket",
                Priority.Medium, 3, null, deadline, dateStart);

        assertEquals(3, ft.getWorkableTime());
        assertEquals(3, ft.tillDue());
    }

    public void testCreationDateBeforeDeadline() {
        Calendar dateStart = Calendar.getInstance();
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DAY_OF_MONTH, -2);

        FeatureTicket ft = new FeatureTicket("ticket", "this is a ticket",
                Priority.Medium, 3, null, deadline, dateStart);

        try {
            ft.getWorkableTime();
            fail();
        } catch (IllegalStateException e) {
        }

        try {
            ft.tillDue();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    public void testNullDeadline() {
        Calendar dateStart = Calendar.getInstance();

        FeatureTicket ft = new FeatureTicket("ticket", "this is a ticket",
                Priority.Medium, 3, null, null, dateStart);

        try {
            ft.getWorkableTime();
            fail();
        } catch (NullPointerException e) {
        }

        try {
            ft.tillDue();
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testNullCreationDate() {
        Calendar deadline = Calendar.getInstance();

        FeatureTicket ft = new FeatureTicket("ticket", "this is a ticket",
                Priority.Medium, 3, null, deadline, null);

        try {
            ft.getWorkableTime();
            fail();
        } catch (NullPointerException e) {
        }

        try {
            ft.tillDue();
            fail();
        } catch (NullPointerException e) {
        }
    }
}
