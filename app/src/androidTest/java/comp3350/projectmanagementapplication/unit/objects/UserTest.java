package comp3350.projectmanagementapplication.objects;

import junit.framework.TestCase;


public class UserTest extends TestCase {
    public void testSameUserName() {
        User user1 = new User("Tom Cruise", "Is an Actor");
        User user2 = new User("Tom Cruise", "Is also an actor.");

        assertTrue(user1.equals(user2));

        User user3 = new User("Iron Man", "Hero with an Iron suit.");
        User user4 = new User("Thor", "Hero with a Hammer.");

        assertFalse(user3.equals(user4));
    }

    public void testUserNullName() {

        User user1 = new User(null, "Is an Actor");
        assertNull(user1.getName());

        User user2 = new User("John Wick", "Is a character");
        assertEquals("John Wick", user2.getName());
    }

}
