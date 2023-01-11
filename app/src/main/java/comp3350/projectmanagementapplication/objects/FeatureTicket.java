package comp3350.projectmanagementapplication.objects;

import java.util.Calendar;

public class FeatureTicket extends Ticket {
    private User assignee;

    public FeatureTicket(String name, String description, Priority priority, int cost, Status status, Calendar deadline, Calendar creation) {
        super(name, description, priority, cost, status, deadline, creation);
    }

    public FeatureTicket(String name, String description, Priority priority, int cost, Status status, Calendar deadline) {
        super(name, description, priority, cost, status, deadline);
    }

    public User getAssignee() {
        return this.assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof FeatureTicket) ? ((FeatureTicket) object).getName().equals(this.getName()) : false;
    }
}
