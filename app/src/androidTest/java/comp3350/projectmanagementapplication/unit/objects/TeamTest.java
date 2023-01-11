package comp3350.projectmanagementapplication.objects;

import junit.framework.TestCase;

import java.util.ArrayList;

public class TeamTest extends TestCase {

    public void testSameTeamName() {
        Team team1 = new Team("Team Alpha", null, null);
        Team team2 = new Team("Team Alpha", null, null);

        assertTrue(team1.equals(team2));

        Team team3 = new Team("Team Beta", null, null);
        Team team4 = new Team("Team Gamma", null, null);

        assertFalse(team3.equals(team4));
    }

    public void testTeamNullName() {

        Team team1 = new Team(null, null, null);
        assertNull(team1.getName());

        Team team2 = new Team("Team Beta", null, null);
        assertEquals("Team Beta", team2.getName());
    }

    public void testMemberList() {
        ArrayList<User> testUserList = new ArrayList<>();

        Team team = new Team("Team A", testUserList, null);
        assertEquals(0, team.getUsers().size());

        User user = new User("Jackie Chan", "Knows KungFu");
        testUserList.add(user);
        user = new User("Bruce Lee", "Knows Karate");
        testUserList.add(user);
        user = new User("Michael Jackson", "Knows dance");
        testUserList.add(user);

        team = new Team("Team B", testUserList, null);
        assertEquals(testUserList, team.getUsers());
        assertEquals(3, team.getUsers().size());
    }

    public void testProjectList() {
        ArrayList<Project> testProjectList = new ArrayList<>();

        Team team = new Team("Team A", null, testProjectList);
        assertEquals(0, team.getProjects().size());

        Project project = new Project("Chess", "Is a two Player game.", null);
        testProjectList.add(project);
        project = new Project("Sudoku", "Can only be played by a single player.", null);
        testProjectList.add(project);
        project = new Project("Ludo", "Can be played by 4 players.", null);
        testProjectList.add(project);

        team = new Team("Team B", null, testProjectList);
        assertEquals(testProjectList, team.getProjects());
        assertEquals(3, team.getProjects().size());
    }
}
