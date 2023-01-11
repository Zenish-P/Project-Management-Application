package comp3350.projectmanagementapplication;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import comp3350.projectmanagementapplication.business.DatabaseAccessorTest;
import comp3350.projectmanagementapplication.business.OperationsTest;
import comp3350.projectmanagementapplication.objects.FeatureTicketTest;
import comp3350.projectmanagementapplication.objects.TeamTest;
import comp3350.projectmanagementapplication.objects.TicketTest;
import comp3350.projectmanagementapplication.objects.UserTest;
import comp3350.projectmanagementapplication.unit.objects.ProjectTest;
import comp3350.projectmanagementapplication.unit.persistence.DatabaseTest;

public class RunUnitTests extends TestCase {
    public static TestSuite suite;

    public static Test suite() {
        System.out.println("Launching Test Suite.");

        RunUnitTests.suite = new TestSuite("Unit tests");
        RunUnitTests.testObjects();
        RunUnitTests.testBusiness();
        RunUnitTests.testPersistence();

        return suite;
    }

    private static void testObjects() {
        suite.addTestSuite(TicketTest.class);
        suite.addTestSuite(FeatureTicketTest.class);
        suite.addTestSuite(UserTest.class);
        suite.addTestSuite(TeamTest.class);
        suite.addTestSuite(ProjectTest.class);
    }

    private static void testBusiness() {
        suite.addTestSuite(OperationsTest.class);
        suite.addTestSuite(DatabaseAccessorTest.class);
    }

    private static void testPersistence() {
        suite.addTestSuite(DatabaseTest.class);
    }
}