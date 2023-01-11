package comp3350.projectmanagementapplication.integration;

import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Calendar;

import comp3350.projectmanagementapplication.App;
import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Priority;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Status;
import comp3350.projectmanagementapplication.objects.Team;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.persistence.DataAccess;
import comp3350.projectmanagementapplication.persistence.HSQLDatabase;

public class DataAccessHSQLDBSeamTest extends TestCase {
    private DataAccess dataAccess;

    @Before
    public void setUp() {
        App.writeDatabaseScript(ApplicationProvider.getApplicationContext(), true);

        dataAccess = new HSQLDatabase();
    }

    public void testDBUser() {
        ArrayList<User> users = dataAccess.getUsers("This team doesnt exist");

        assertEquals(0, users.size());

        users = dataAccess.getUsers("Team #2");
        assertTrue(users.contains(new User("Leonardo", "")));
        assertEquals(4, users.size());

        User newUser = new User("foo", "bar");

        try {
            dataAccess.addUser("Team #2", newUser);
        } catch (Exception e){
            fail();
        }

        users = dataAccess.getUsers("Team #2");
        assertTrue(users.contains(new User("foo", "")));
        assertEquals(5, users.size());

        try {
            // Pablo is an existing user in team 2
            dataAccess.addUser("Team #2", new User("Pablo", "Likes dogs"));
            fail();
        } catch (Exception e){ }


        try {
            dataAccess.removeUser("Team #2", "Leonardo");
            dataAccess.addUser("Team #2", new User("Leonardo", "Doesnt like dogs"));
        } catch (Exception e){
            fail();
        }

        try {
            dataAccess.removeUser("Team #1", "Graydon");
        } catch (Exception e) {
            fail();
        }
        users = dataAccess.getUsers("Team #1");
        assertFalse(users.contains(new User("Graydon", "")));
    }

    public void testDBTeams() {
        // this team does not exist
        Team team = dataAccess.getTeam("Team #45");
        assertEquals(null, team);

        team = dataAccess.getTeam("Team #2");
        assertEquals("Team #2", team.getName());

        ArrayList<Team> teams = dataAccess.getTeams();
        assertEquals(3, teams.size());
        assertTrue(teams.contains(new Team("Team #2")));

        try{
            dataAccess.addUser("This team does not exist", new User("foo", "bar"));
            fail();
        }catch (Exception e){}

        try{
            dataAccess.addTeam(new Team("Team #1"));
            fail();
        }catch(Exception e){}

        try{
            dataAccess.addTeam(new Team("Team #3"));
        }catch(Exception e){
            fail();
        }

        teams = dataAccess.getTeams();
        assertEquals(4, teams.size());
        assertTrue(teams.contains(new Team("Team #3")));
    }

    public void testDBProject() {
        ArrayList<Project> projects = dataAccess.getProjects("Team #2");
        assertEquals(2, projects.size());

        try {
            // Role-playing Game already exists
            dataAccess.addProject("Team #2", new Project("Role-playing Game", "Not an RPG game"));
            fail();
        }catch(Exception e) { }

        try {
            // Role-playing Game already exists
            dataAccess.addProject("Team #1", new Project("Role-playing Game", "Not an RPG game"));
        }catch(Exception e) {
            fail();
        }

        try {
            // Role-playing Game already exists
            dataAccess.addProject("Team does not exist", new Project("Other Role-playing Game", ""));
            fail();
        }catch(Exception e) { }

        projects = dataAccess.getProjects("Team #1");
        assertTrue(projects.contains(new Project("Role-playing Game", "", null)));
        assertEquals(3, projects.size());

        Project project = dataAccess.getProject("Team #54", "Role-playing Game");
        assertNull(project);

        project = dataAccess.getProject("Team #2", "Not a project");
        assertNull(project);

        project = dataAccess.getProject("Team #2", "Role-playing Game");
        assertEquals("Role-playing Game", project.getName());
    }

    public void testDBTickets() {
        ArrayList<FeatureTicket> tickets = dataAccess.getFeatureTickets("Team #1", "Website");
        assertEquals(5, tickets.size());
        assertTrue(tickets.contains(new FeatureTicket("Add a company info page", "", null, 0, null, null)));

        tickets = dataAccess.getFeatureTickets("non existent team", "iOS App");
        assertEquals(0, tickets.size());

        tickets = dataAccess.getFeatureTickets("Team #2", "project doesnt exist");
        assertEquals(0, tickets.size());

        try {
            // this ticket already exists
            dataAccess.addFeatureTicket("Team #2", "Platforming Game",
                    new FeatureTicket("Implement a movement system", "", null, 0, null, null));
            fail();
        } catch(Exception e){}

        try {
            dataAccess.addFeatureTicket("Team #2", "Platforming Game",
                    new FeatureTicket("New ticket", "", Priority.Low, 0, Status.TODO, Calendar.getInstance()));
        }catch(Exception e){
            fail();
        }

        tickets = dataAccess.getFeatureTickets("Team #2", "Platforming Game");
        assertEquals(5, tickets.size());
        assertTrue(tickets.contains(new FeatureTicket("New ticket", "", Priority.Low, 0, Status.TODO, Calendar.getInstance())));
    }

    public void testDBPersistence() {
        try {
            dataAccess.addTeam(new Team("new team"));
        } catch (Exception e){
            fail();
        }

        DataAccess SecondaryDataAccess = HSQLDatabase.getInstance();

        Team team = dataAccess.getTeam("new team");
        assertNotNull(team);

        team = SecondaryDataAccess.getTeam("new team");
        assertNotNull(team);
    }
}