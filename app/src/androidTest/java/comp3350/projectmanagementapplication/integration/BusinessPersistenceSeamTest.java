package comp3350.projectmanagementapplication.integration;

import junit.framework.TestCase;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Calendar;

import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Priority;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Status;
import comp3350.projectmanagementapplication.objects.Team;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.persistence.StubDatabase;

public class BusinessPersistenceSeamTest extends TestCase {
    private DatabaseAccessor databaseAccessor;

    @Before
    public void setUp() {
        databaseAccessor = new DatabaseAccessor(new StubDatabase());
    }

    public void testUserAccesses() {
        ArrayList<User> users;
        boolean result;

        users= databaseAccessor.getUsers("Team #1");
        assertTrue(users.size() == 4);
        assertTrue(users.contains(new User("Graydon","")));

        result = databaseAccessor.addUser("Team #1",new User("Jeff","Likes burgers"));
        assertTrue(result);

        users = databaseAccessor.getUsers("Team #1");
        assertTrue(users.size() == 5);
        assertTrue(users.contains(new User("Jeff","")));

        result = databaseAccessor.removeUser("Team #1","Guido");
        assertTrue(result);
        users = databaseAccessor.getUsers("Team #1");
        assertTrue(users.size() == 4);
        assertFalse(users.contains(new User("Guido","")));

        result = databaseAccessor.removeUser("Team #1","Graydon");
        assertTrue(result);
        users = databaseAccessor.getUsers("Team #1");
        assertTrue(users.size() == 3);
        assertFalse(users.contains(new User("Graydon","")));


        result = databaseAccessor.removeUser("Team #1","Brendan");
        assertTrue(result);
        users = databaseAccessor.getUsers("Team #1");
        assertTrue(users.size() == 2);
        assertFalse(users.contains(new User("Brendan","")));


        result = databaseAccessor.removeUser("Team #1","Dennis");
        assertTrue(result);
        users = databaseAccessor.getUsers("Team #1");
        assertTrue(users.size() == 1);
        assertFalse(users.contains(new User("Dennis","")));

        result = databaseAccessor.removeUser("Team #1","Jeff");
        assertTrue(result);
        users = databaseAccessor.getUsers("Team #1");
        assertTrue(users.size() == 0);
        assertFalse(users.contains(new User("Jeff","")));


        result = databaseAccessor.updateUser("Team #2","Leonardo",new User("Robert","Likes Hamsters"));
        assertTrue(result);
        users = databaseAccessor.getUsers("Team #2");
        assertTrue(users.size() == 4);
        assertTrue(users.contains(new User("Robert","")));
        assertFalse(users.contains(new User("Leonardo","")));


        result = databaseAccessor.updateUser("Team #2","Robert",new User("King","Likes Lion"));
        assertTrue(result);
        users = databaseAccessor.getUsers("Team #2");
        assertTrue(users.size() == 4);
        assertTrue(users.contains(new User("King","")));
        assertFalse(users.contains(new User("Robert","")));

        result = databaseAccessor.addUser("Team #2", new User("Jack","Likes elephant"));
        assertTrue(result);
        users = databaseAccessor.getUsers("Team #2");
        assertTrue(users.size() == 5);
        assertTrue(users.contains(new User("Jack","")));
    }

    public void testProjectAccesses() {
        ArrayList<Project> projectList;
        Project project;
        boolean result;

        projectList = databaseAccessor.getProjects("Team #1");
        assertTrue(projectList.size() == 2);

        assertTrue(projectList.contains(new Project("Website","",null)));
        result = databaseAccessor.addProject("Team #1",new Project("Android Application","",null));
        assertTrue(result);
        projectList = databaseAccessor.getProjects("Team #1");
        assertTrue(projectList.size() == 3);
        assertTrue(projectList.contains(new Project("Android Application","",null)));

        result = databaseAccessor.addProject("Team #1",new Project("Windows Application","",null));
        assertTrue(result);
        projectList = databaseAccessor.getProjects("Team #1");
        assertTrue(projectList.size() == 4);
        assertTrue(projectList.contains(new Project("Windows Application","",null)));

        project = databaseAccessor.getProject("Team #1","Windows Application");
        assertTrue(project.getName().equals("Windows Application"));

        project = databaseAccessor.getProject("Team #1","Windows Application");
        assertTrue(project.getName().equals("Windows Application"));
    }

    public void testTeamAccesses() {
        ArrayList<Team> teamList;
        Team team;
        boolean result;

        teamList = databaseAccessor.getTeams();
        assertTrue(teamList.size() == 2);

        team = databaseAccessor.getTeam("Team #1");
        assertTrue(team.getName().equals("Team #1"));
        assertFalse(team.getName().equals("Team #2"));

        result = databaseAccessor.addTeam(new Team("Team #3"));
        assertTrue(result);
        teamList = databaseAccessor.getTeams();
        assertTrue(teamList.size() == 3);

        result = databaseAccessor.addTeam(new Team("Team #4"));
        assertTrue(result);
        teamList = databaseAccessor.getTeams();
        assertTrue(teamList.size() == 4);

        team = databaseAccessor.getTeam("Team #4");
        assertTrue(team.getName().equals("Team #4"));
    }
    
    public void testFeatureTicketAccesses() {
        boolean result;
        ArrayList<FeatureTicket> featureTickets;
        featureTickets = databaseAccessor.getFeatureTickets("Team #1","Website");
        assertTrue(featureTickets.contains(new FeatureTicket("Create a product page","Create a product page allowing users to view and purchase products", Priority.High,4, Status.Complete, Calendar.getInstance())));

        result = databaseAccessor.addFeatureTicket("Team #1","Website",new FeatureTicket("temporaryTicket 1","",Priority.High,2,Status.TODO,Calendar.getInstance()));
        assertTrue(result);

        featureTickets = databaseAccessor.getFeatureTickets("Team #1","Website");
        assertTrue(featureTickets.size() == 6);

        featureTickets = databaseAccessor.getFeatureTickets("Team #2","Platforming Game");
        assertTrue(featureTickets.size() == 4);
        result = databaseAccessor.addFeatureTicket("Team #2","Platforming Game",new FeatureTicket("temporaryTicket 2","",Priority.High,10,Status.TODO,Calendar.getInstance()));
        assertTrue(result);

        featureTickets = databaseAccessor.getFeatureTickets("Team #2","Platforming Game");
        assertTrue(featureTickets.size() == 5);
        FeatureTicket updatedTicket = new FeatureTicket("Create a new updated product page","Create a new updated product page allowing users to view and purchase products", Priority.High,4, Status.Complete, Calendar.getInstance());

        result = databaseAccessor.updateFeatureTicket("Team #1","Website","Create a product page",updatedTicket);
        assertTrue(result);

        featureTickets = databaseAccessor.getFeatureTickets("Team #1","Website");
        assertFalse(featureTickets.contains(new FeatureTicket("Create a product page","Create a product page allowing users to view and purchase products", Priority.High,4, Status.Complete, Calendar.getInstance())));
        assertTrue(featureTickets.contains(updatedTicket));
        assertTrue(featureTickets.size() == 6);
    }
}
