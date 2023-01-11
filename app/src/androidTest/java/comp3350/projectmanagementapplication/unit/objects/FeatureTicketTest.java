package comp3350.projectmanagementapplication.objects;

import junit.framework.TestCase;

import org.junit.Before;

import java.util.Calendar;

public class FeatureTicketTest extends TestCase {

    Project project;
    Iteration iteration;
    Calendar weekFromNow;

    @Before
    public void setUp() {
        project = new Project("project", "", null);
        iteration = new Iteration(project, Calendar.getInstance(), weekFromNow);
        weekFromNow = Calendar.getInstance();
        weekFromNow.add(Calendar.DAY_OF_MONTH, 7);
    }

    public void testStandardInput() {
        FeatureTicket featureTicket = new FeatureTicket("ticket", "my ticket", Priority.High, 5, Status.TODO, weekFromNow);

        assertEquals("ticket", featureTicket.getName());
        assertEquals("my ticket", featureTicket.getDescription());
        assertEquals(Priority.High, featureTicket.getPriority());
        assertEquals(5, featureTicket.getCost());
        assertNull(featureTicket.getAssignee());
        assertEquals(Status.TODO, featureTicket.getStatus());
    }

    public void testNullInput() {
        FeatureTicket featureTicket = new FeatureTicket(null, null, null, 0, null, null);

        assertNull(featureTicket.getName());
        assertNull(featureTicket.getDescription());
    }

    public void testSetAssignee() {
        User user = new User("foo", "bar");

        FeatureTicket featureTicket = new FeatureTicket("ticket", "my ticket", Priority.High, 5, Status.TODO, weekFromNow);

        featureTicket.setAssignee(user);
        assertEquals(user, featureTicket.getAssignee());
    }

    public void testSetStatus() {
        FeatureTicket featureTicket = new FeatureTicket("ticket", "my ticket", Priority.High, 5, Status.TODO, weekFromNow);

        assertEquals(Status.TODO, featureTicket.getStatus());
        featureTicket.setStatus(Status.InProgress);
        assertEquals(Status.InProgress, featureTicket.getStatus());
    }

}


