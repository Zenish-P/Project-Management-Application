package comp3350.projectmanagementapplication.business;

import junit.framework.TestCase;

import java.util.Calendar;

import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Priority;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Status;
import comp3350.projectmanagementapplication.objects.Team;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.persistence.StubDatabase;

public class DatabaseAccessorTest extends TestCase {
    private DatabaseAccessor databaseAccessor;

    @Override
    public void setUp(){
        this.databaseAccessor = new DatabaseAccessor(
            new StubDatabase()
        );
    }

    public void testGetUserList(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        int prevUserCount = this.databaseAccessor.getUsers("teamAlpha").size();

        this.databaseAccessor.addUser("teamAlpha",new User("User #1","first user"));
        this.databaseAccessor.addUser("teamAlpha",new User("User #2","second user"));
        this.databaseAccessor.addUser("teamAlpha",new User("User #3","third user"));


        assertEquals("User #2",this.databaseAccessor.getUsers("teamAlpha").get(prevUserCount+1).getName());
        assertEquals("second user",this.databaseAccessor.getUsers("teamAlpha").get(prevUserCount+1).getBio());
    }

    public void testAddUser(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        int prevUserCount = this.databaseAccessor.getUsers("teamAlpha").size();

        this.databaseAccessor.addUser("teamAlpha",new User("User #1","first user"));
        this.databaseAccessor.addUser("teamAlpha",new User("User #2","second user"));
        assertEquals(prevUserCount+2,this.databaseAccessor.getUsers("teamAlpha").size());

    }

    public void testAddDuplicateUser(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        int prevUserCount = this.databaseAccessor.getUsers("teamAlpha").size();

        this.databaseAccessor.addUser("teamAlpha",new User("User #1","first user"));
        this.databaseAccessor.addUser("teamAlpha",new User("User #1","This is a duplicate User"));
        this.databaseAccessor.addUser("teamAlpha", new User("User #3","third user"));

        assertEquals(this.databaseAccessor.getUsers("teamAlpha").size(),prevUserCount+2);
    }

    public void testRemoveUser(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        int prevUserCount = this.databaseAccessor.getUsers("teamAlpha").size();

        this.databaseAccessor.addUser("teamAlpha",new User("User #1","first user"));
        this.databaseAccessor.addUser("teamAlpha",new User("User #2","second user"));
        this.databaseAccessor.addUser("teamAlpha",new User("User #3","third user"));

        assertEquals(prevUserCount+3,this.databaseAccessor.getUsers("teamAlpha").size());

        this.databaseAccessor.removeUser("teamAlpha","User #2");
        this.databaseAccessor.removeUser("teamAlpha","User #3");

        assertEquals(prevUserCount+1,this.databaseAccessor.getUsers("teamAlpha").size());
    }

    public void testRemoveNonExistingUser(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        int prevUserCount = this.databaseAccessor.getUsers("teamAlpha").size();

        this.databaseAccessor.addUser("teamAlpha",new User("User #1","first user"));
        this.databaseAccessor.addUser("teamAlpha",new User("User #2","second user"));
        this.databaseAccessor.addUser("teamAlpha",new User("User #3","third user"));

        assertEquals(prevUserCount+3,this.databaseAccessor.getUsers("teamAlpha").size());

        this.databaseAccessor.removeUser("teamAlpha","User #2");
        this.databaseAccessor.removeUser("teamAlpha","User #4");

        assertEquals(prevUserCount+2,this.databaseAccessor.getUsers("teamAlpha").size());
    }

    public void testUpdateUser(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        int prevUserCount = this.databaseAccessor.getUsers("teamAlpha").size();
        User toUpdate = new  User("User #1","first user");

        this.databaseAccessor.addUser("teamAlpha",toUpdate);
        this.databaseAccessor.addUser("teamAlpha",new User("User #2","second user"));
        this.databaseAccessor.addUser("teamAlpha",new User("User #3","third user"));

        toUpdate = new User("User #10","first user");
        this.databaseAccessor.updateUser("teamAlpha","User #1",toUpdate);
        assertEquals("User #10",this.databaseAccessor.getUsers("teamAlpha").get(2).getName());
    }


    public void testAddTeam(){
        int prevTeamCount = this.databaseAccessor.getTeams().size();

        this.databaseAccessor.addTeam(new Team("TeamAlpha"));
        this.databaseAccessor.addTeam(new Team("TeamBetta"));
        this.databaseAccessor.addTeam(new Team("TeamGamma"));
        this.databaseAccessor.addTeam(new Team("TeamYotta"));

        assertEquals(prevTeamCount + 4,this.databaseAccessor.getTeams().size());
    }

    public void testGetTeams(){
        int prevTeamCount = this.databaseAccessor.getTeams().size();

        this.databaseAccessor.addTeam(new Team("TeamAlpha"));
        this.databaseAccessor.addTeam(new Team("TeamBetta"));
        this.databaseAccessor.addTeam(new Team("TeamGamma"));
        this.databaseAccessor.addTeam(new Team("TeamYotta"));

        assertEquals("TeamBetta",this.databaseAccessor.getTeams().get(prevTeamCount+1).getName());
        assertEquals("TeamGamma",this.databaseAccessor.getTeams().get(prevTeamCount+2).getName());
    }

    public void testGetTeam(){
        Team alpha = new Team("TeamAlpha");
        Team betta = new Team("TeamBetta");
        Team gamma = new Team("TeamGamma");

        this.databaseAccessor.addTeam(alpha);
        this.databaseAccessor.addTeam(betta);
        this.databaseAccessor.addTeam(gamma);

        assertEquals(betta,this.databaseAccessor.getTeam("TeamBetta"));
        assertEquals(gamma,this.databaseAccessor.getTeam("TeamGamma"));
    }

    public void testGetTeamEdge(){
        Team alpha = new Team("TeamAlpha");
        Team betta = new Team("TeamBetta");
        Team yotta = new Team("TeamYotta");

        this.databaseAccessor.addTeam(alpha);
        this.databaseAccessor.addTeam(betta);

        assertNull(this.databaseAccessor.getTeam("TeamYotta"));
    }

    public void testAddProject(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        int prevProjectCount = this.databaseAccessor.getProjects("teamAlpha").size();

        this.databaseAccessor.addProject("teamAlpha",new Project("ProjectA","This is Project A."));
        this.databaseAccessor.addProject("teamAlpha",new Project("ProjectB","This is Project B."));
        this.databaseAccessor.addProject("teamAlpha",new Project("ProjectC","This is Project C."));

        assertEquals(prevProjectCount+3,this.databaseAccessor.getProjects("teamAlpha").size());
    }

    public void testAddDuplicateProject(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        int prevProjectCount = this.databaseAccessor.getProjects("teamAlpha").size();

        this.databaseAccessor.addProject("teamAlpha",new Project("ProjectA","This is Project A."));
        this.databaseAccessor.addProject("teamAlpha",new Project("ProjectA","This is duplicate project."));
        this.databaseAccessor.addProject("teamAlpha",new Project("ProjectC","This is Project C."));

        assertEquals(prevProjectCount+2,this.databaseAccessor.getProjects("teamAlpha").size());
    }

    public void testGetProject(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        Project A = new Project("ProjectA","This is Project A.");
        Project B = new Project("ProjectB","This is Project B.");
        Project C = new Project("ProjectC","This is Project C.");

        this.databaseAccessor.addProject("teamAlpha",A);
        this.databaseAccessor.addProject("teamAlpha",B);
        this.databaseAccessor.addProject("teamAlpha",C);

        assertEquals(C,this.databaseAccessor.getProject("teamAlpha","ProjectC"));
        assertEquals(B,this.databaseAccessor.getProject("teamAlpha","ProjectB"));

        this.databaseAccessor.addTeam(new Team("teamBetta"));

        Project D =new Project("ProjectD","This is Project D.");
        Project E =new Project("ProjectE","This is Project E.");
        this.databaseAccessor.addProject("teamBetta",D);
        this.databaseAccessor.addProject("teamBetta",E);

        assertEquals(E,this.databaseAccessor.getProject("teamBetta","ProjectE"));
    }
    public void testGetProjectFail(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        this.databaseAccessor.addTeam(new Team("teamBetta"));

        Project A = new Project("ProjectA","This is Project A.");
        Project B = new Project("ProjectB","This is Project B.");
        Project C =new Project("ProjectC","This is Project C.");
        Project D =new Project("ProjectD","This is Project D.");

        this.databaseAccessor.addProject("teamAlpha",A);
        this.databaseAccessor.addProject("teamAlpha",B);
        this.databaseAccessor.addProject("teamBetta",C);
        this.databaseAccessor.addProject("teamBetta",D);

        assertNull(this.databaseAccessor.getProject("teamAlpha","ProjectC"));
        assertNull(this.databaseAccessor.getProject("teamBeta","ProjectA"));

    }
    public void testGetProjects(){
        this.databaseAccessor.addTeam(new Team("teamAlpha"));

        int prevProjectCount = this.databaseAccessor.getProjects("teamAlpha").size();

        Project A = new Project("ProjectA","This is Project A.");
        Project B = new Project("ProjectB","This is Project B.");
        Project C =new Project("ProjectC","This is Project C.");

        this.databaseAccessor.addProject("teamAlpha",A);
        this.databaseAccessor.addProject("teamAlpha",B);
        this.databaseAccessor.addProject("teamAlpha",C);

        assertEquals(B,this.databaseAccessor.getProjects("teamAlpha").get(1));
        assertEquals(prevProjectCount+3,this.databaseAccessor.getProjects("teamAlpha").size());
        assertEquals("ProjectC",this.databaseAccessor.getProjects("teamAlpha").get(2).getName());
    }

    public void testGetTickets() {
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        this.databaseAccessor.addProject("teamAlpha", new Project("ProjectA","This is Project A."));

        Calendar deadline = Calendar.getInstance();
        deadline.set(2022, 9, 20);
        Calendar creationDate = Calendar.getInstance();
        creationDate.set(2022, 7, 20);


        FeatureTicket A = new FeatureTicket("ticketA", "this is ticketA", Priority.Low, 5, Status.TODO, deadline, creationDate);

        this.databaseAccessor.addFeatureTicket("teamAlpha", "ProjectA", A);
        assertTrue(this.databaseAccessor.getFeatureTickets("teamAlpha", "ProjectA").contains(A));
    }
    public void testUpdateTicket(){
        boolean result;
        this.databaseAccessor.addTeam(new Team("teamAlpha"));
        this.databaseAccessor.addProject("teamAlpha", new Project("ProjectA","This is Project A."));

        Calendar deadline = Calendar.getInstance();
        deadline.set(2022, 9, 20);
        Calendar creationDate = Calendar.getInstance();
        creationDate.set(2022, 7, 20);


        FeatureTicket A = new FeatureTicket("ticketA", "this is ticketA", Priority.Low, 5, Status.TODO, deadline, creationDate);
        FeatureTicket B =new FeatureTicket("ticketB", "this is updated new ticketB", Priority.Low, 5, Status.TODO, deadline, creationDate);
        this.databaseAccessor.addFeatureTicket("teamAlpha", "ProjectA", A);
        result = this.databaseAccessor.updateFeatureTicket("teamAlpha","ProjectA","ticketA",B);
        assertTrue(result);
        assertFalse(this.databaseAccessor.getFeatureTickets("teamAlpha", "ProjectA").contains(A));
        assertTrue(this.databaseAccessor.getFeatureTickets("teamAlpha", "ProjectA").contains(B));

    }


}