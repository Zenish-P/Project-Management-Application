package comp3350.projectmanagementapplication.objects;

public class UserFeatureTicket {
    private final User user;
    private final FeatureTicket featureTicket;

    public UserFeatureTicket(User user, FeatureTicket featureTicket) {
        this.user = user;
        this.featureTicket = featureTicket;
    }

    public FeatureTicket getFeatureTicket() {
        return featureTicket;
    }

    public User getUser() {
        return user;
    }
}
