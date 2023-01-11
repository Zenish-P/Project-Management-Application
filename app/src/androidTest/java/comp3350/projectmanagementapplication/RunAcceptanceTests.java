package comp3350.projectmanagementapplication;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.projectmanagementapplication.acceptance.ProjectSelectTest;
import comp3350.projectmanagementapplication.acceptance.TeamSelectTest;
import comp3350.projectmanagementapplication.acceptance.TicketBoardTest;
import comp3350.projectmanagementapplication.acceptance.UserSelectTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TeamSelectTest.class,
    UserSelectTest.class,
    ProjectSelectTest.class,
    TicketBoardTest.class
})
public class RunAcceptanceTests {}