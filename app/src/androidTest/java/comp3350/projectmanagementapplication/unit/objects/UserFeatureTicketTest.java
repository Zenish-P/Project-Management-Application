package comp3350.projectmanagementapplication.objects;

import junit.framework.TestCase;

public class UserFeatureTicketTest extends TestCase {

    public void testStandardUserFeatureTicket() {
        User user = new User("user", "");
        FeatureTicket featureTicket = new FeatureTicket("ticket", "", Priority.High, 3, null, null);

        UserFeatureTicket userFeatureTicket = new UserFeatureTicket(user, featureTicket);

        assertEquals("ticket", userFeatureTicket.getFeatureTicket().getName());
        assertEquals("user", userFeatureTicket.getUser().getName());
    }

    public void testNullUserFeatureTicket() {
        UserFeatureTicket userFeatureTicket = new UserFeatureTicket(null, null);

        assertNull(userFeatureTicket.getFeatureTicket());
        assertNull(userFeatureTicket.getUser());
    }


}
