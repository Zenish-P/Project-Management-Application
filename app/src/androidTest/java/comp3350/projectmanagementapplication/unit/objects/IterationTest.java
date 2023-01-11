package comp3350.projectmanagementapplication.objects;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class IterationTest extends TestCase {

    Calendar today, tomorrow, yesterday;
    Iteration iterationA, iterationB, iterationTime;
    Project project, project1, project2;


    @Before
    public void setup() {
        today = Calendar.getInstance();
        tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
    }


    private void initializeIterations() {
        setup();

        iterationA = new Iteration(null, yesterday, today);


        project = new Project("Blockchain", "This is a blockchain project.", null);
        project1 = new Project("Blockrope", "This is a rope.", null);
        project2 = new Project("Blockblock", "This is a block in block.", null);

        iterationB = new Iteration(project1, today, tomorrow);
        iterationTime = new Iteration(project2, null, null);
    }


    public void testIterationCreationCount() {


        Iteration iteration0 = new Iteration(null, null, null);

        int origionalID = iteration0.getID();

        Iteration iteration1 = new Iteration(null, null, null);
        Iteration iteration2 = new Iteration(null, null, null);


        assertEquals(iteration2.getID(), origionalID + 2);
    }


    public void testConstructorTimeCheck() {
        setup();

        Iteration iterationTimeError = new Iteration(null, tomorrow, yesterday);

        assertEquals(iterationTimeError.getStart(), iterationTimeError.getEnd());

        Iteration iterationTimeCorrect = new Iteration(null, yesterday, tomorrow);

        assert (iterationTimeCorrect.getStart().compareTo(iterationTimeCorrect.getEnd()) != 0);
        assertEquals(iterationTimeCorrect.getStart(), yesterday);
        assertEquals(iterationTimeCorrect.getEnd(), tomorrow);

    }

    @Test
    public void GetterStart() {
        initializeIterations();

        Calendar start = iterationB.getStart();
        assertNotNull(start);
        assertEquals(start, today);
    }


    @Test
    public void GetterEnd() {
        initializeIterations();

        Calendar end = iterationB.getEnd();
        assertNotNull(end);
        assertEquals(end, tomorrow);
    }


    public void testSetProject() {
        initializeIterations();

        iterationA.setProject(project);
        assertNotNull(iterationA.getProject());

        iterationA.setProject(project1);
        assertEquals(project1.getName(), iterationA.getProject().getName());
    }


    public void testSetTimeError() {
        initializeIterations();

        iterationTime.setStart(tomorrow);
        iterationTime.setEnd(yesterday);

        assertNull(iterationTime.getEnd());


        iterationTime.setStart(null);
        iterationTime.setEnd(today);
        iterationTime.setStart(tomorrow);

        assertNull(iterationTime.getStart());

    }


}
