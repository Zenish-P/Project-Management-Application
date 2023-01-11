package comp3350.projectmanagementapplication.unit.objects;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Priority;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Status;

public class ProjectTest extends TestCase {

    public void testEquals() {
        Project project1 = new Project("Chess App", "A game made to be played by 2 human players", null);
        Project project2 = new Project("Chess App", "A game where a human player competes with Artificial Intelligence", null);

        assertEquals(true, project1.equals(project2));

        Project project3 = new Project("Ludo App", "A game where 4 players compete against each other.", null);
        Project project4 = new Project("Monopoly App", "A game where 4 players compete against each other.", null);

        assertEquals(false, project3.equals(project4));
    }

    public void testProjectNullName() {
        Project project1 = new Project(null, "A game made to be played by 2 human players", null);
        assertNull(project1.getName());

        Project project2 = new Project("Chess App", "A game made to be played by 2 human players", null);
        assertEquals("Chess App", project2.getName());
    }

    public void testFeatureTicketList() {
        ArrayList<FeatureTicket> testFeatureTicketList = new ArrayList<>();
        Project project = new Project("Chess App", "A game made to be played by 2 human players", testFeatureTicketList);
        assertEquals(0, project.getFeatureTickets().size());

        FeatureTicket featureTicket = new FeatureTicket("FeatureTicketA", "This is a feature Ticket", Priority.High, 2, null, null);
        testFeatureTicketList.add(featureTicket);
        featureTicket = new FeatureTicket("FeatureTicketB", "This is a feature Ticket", Priority.High, 2, null, null);
        testFeatureTicketList.add(featureTicket);
        featureTicket = new FeatureTicket("FeatureTicketC", "This is a feature Ticket", Priority.High, 2, null, null);
        testFeatureTicketList.add(featureTicket);

        assertEquals(testFeatureTicketList, project.getFeatureTickets());
        assertEquals(3, project.getFeatureTickets().size());
    }

    public void testStandardProject() {
        ArrayList<FeatureTicket> testFeatureTicketList = new ArrayList<>();

        Project project = new Project("Chess App", "A game made to be played by 2 human players", testFeatureTicketList);

        FeatureTicket featureTicket = new FeatureTicket("FeatureTicketA", "This is a feature Ticket", Priority.High, 2, Status.TODO, null);
        testFeatureTicketList.add(featureTicket);
        featureTicket = new FeatureTicket("FeatureTicketB", "This is a feature Ticket", Priority.High, 2, Status.TODO, null);
        testFeatureTicketList.add(featureTicket);
        featureTicket = new FeatureTicket("FeatureTicketC", "This is a feature Ticket", Priority.High, 2, Status.TODO, null);
        testFeatureTicketList.add(featureTicket);


        assertEquals("Chess App", project.getName());
        assertEquals("A game made to be played by 2 human players", project.getDescription());
        project.setDescription("A two player game");
        assertEquals("A two player game", project.getDescription());
    }
}
