package comp3350.projectmanagementapplication.unit.persistence;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Team;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.persistence.StubDatabase;

public class DatabaseTest extends TestCase {
    private Team team;
    private User user;
    private Project project;
    private FeatureTicket featureTicket;

    private static StubDatabase db = StubDatabase.getInstance();

    @Override
    public void setUp() {
        this.team = new Team("Team");
        this.user = new User("User", null);
        this.project = new Project("Project", null, new ArrayList<>());
        this.featureTicket = new FeatureTicket(
                "FeatureTicket",
                null, null,
                0, null, null
        );

        db.getTeams().clear();
    }

    @Override
    public void tearDown() {
        db.reset();
    }

    public void testGetInstance() {

        assertNotNull(db);
        assertSame(db, StubDatabase.getInstance());
    }

    public void testGetExistingTeam() throws Exception {
        db.addTeam(this.team);
        Team team = db.getTeam(this.team.getName());

        assertSame(team, this.team);
    }

    public void testGetNonExistentTeam() {
        Team nonExistentTeam = db.getTeam("_");

        assertNull(nonExistentTeam);
    }

    public void testGetTeams() throws Exception {
        db.addTeam(this.team);
        ArrayList<Team> teams = db.getTeams();

        assertNotNull(teams);
        assertTrue(teams.size() == 1);
        assertTrue(teams.contains(this.team));
    }

    public void testGetExistingProject() throws Exception {
        this.projectTestSetup();

        Project project = db.getProject(this.team.getName(), this.project.getName());

        assertSame(project, this.project);
    }

    public void testGetNonExistentProject() {
        Project nonExistentProject1 = db.getProject(this.team.getName(), "_");
        Project nonExistentProject2 = db.getProject("_", this.project.getName());

        assertNull(nonExistentProject1);
        assertNull(nonExistentProject2);
    }

    public void testGetProjects() throws Exception {
        this.projectTestSetup();

        ArrayList<Project> teamProjects = db.getProjects(this.team.getName());

        assertNotNull(teamProjects);
        assertTrue(teamProjects.size() == 1);
        assertSame(teamProjects.get(0), this.project);
    }

    public void testGetProjectsForNonExistentTeam() {
        ArrayList<Project> nonExistentTeamProjects = db.getProjects("_");

        assertNull(nonExistentTeamProjects);
    }

    public void testGetFeatureTickets() throws Exception {
        this.featureTicketTestSetup();

        ArrayList<FeatureTicket> featureTickets = db.getFeatureTickets(this.team.getName(), this.project.getName());

        assertNotNull(featureTickets);
        assertTrue(featureTickets.size() == 1);
        assertSame(featureTickets.get(0), this.project.getFeatureTickets().get(0));
    }

    public void testAddTeam() throws Exception {
        db.addTeam(this.team);

        assertTrue(db.getTeams().size() == 1);
    }

    public void testAddNullTeam() throws Exception {
        db.addTeam(null);

        assertTrue(db.getTeams().isEmpty());
    }

    public void testAddProject() throws Exception {
        db.addTeam(this.team);
        db.addProject(this.team.getName(), this.project);

        assertTrue(db.getProjects(this.team.getName()).size() == 1);
    }

    public void testAddNullProject() throws Exception {
        db.addTeam(this.team);
        db.addProject(this.team.getName(), null);

        assertTrue(db.getProjects(this.team.getName()).isEmpty());
    }

    public void testAddUser() throws Exception {
        this.projectTestSetup();
        db.addUser(this.team.getName(), this.user);

        assertTrue(db.getUsers(this.team.getName()).size() == 1);
    }

    public void testAddNullUser() throws Exception {
        this.projectTestSetup();
        db.addUser(this.team.getName(), null);

        assertTrue(db.getUsers(this.team.getName()).isEmpty());
    }

    public void testRemoveUser() throws Exception {
        this.projectTestSetup();
        db.addUser(this.team.getName(), this.user);

        db.removeUser(this.team.getName(), this.user.getName());

        assertTrue(db.getUsers(this.team.getName()).isEmpty());
    }

    public void testAddFeatureTicket() throws Exception {
        this.projectTestSetup();
        db.addFeatureTicket(this.team.getName(), this.project.getName(), this.featureTicket);

        assertTrue(db.getFeatureTickets(this.team.getName(), this.project.getName()).size() == 1);
    }

    public void testAddNullFeatureTicket() throws Exception {
        this.projectTestSetup();
        db.addFeatureTicket(this.team.getName(), this.project.getName(), null);

        assertTrue(db.getFeatureTickets(this.team.getName(), this.project.getName()).size() == 0);
    }

    private void projectTestSetup() throws Exception {
        db.addTeam(this.team);
        db.addProject(this.team.getName(), this.project);
    }

    private void featureTicketTestSetup() throws Exception {
        this.projectTestSetup();
        db.addFeatureTicket(this.team.getName(), this.project.getName(), this.featureTicket);
    }
}
