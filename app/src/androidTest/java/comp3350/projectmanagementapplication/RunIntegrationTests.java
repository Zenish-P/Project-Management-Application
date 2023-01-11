package comp3350.projectmanagementapplication;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import comp3350.projectmanagementapplication.integration.BusinessPersistenceSeamTest;
import comp3350.projectmanagementapplication.integration.DataAccessHSQLDBSeamTest;
import comp3350.projectmanagementapplication.presentation.UserSelectActivity;

public class RunIntegrationTests extends TestCase {
    public static TestSuite suite;

    public static Test suite() {
        System.out.println("Launching integration tests");

        RunIntegrationTests.suite = new TestSuite("Integration Tests");
        RunIntegrationTests.suite.addTestSuite(BusinessPersistenceSeamTest.class);
        RunIntegrationTests.suite.addTestSuite(DataAccessHSQLDBSeamTest.class);

        return suite;
    }
}
