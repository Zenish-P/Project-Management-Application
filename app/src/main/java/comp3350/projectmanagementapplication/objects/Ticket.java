package comp3350.projectmanagementapplication.objects;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public abstract class Ticket {
    protected Status status;
    private String name;
    private String description;
    private Priority priority;
    private int cost;
    private Calendar creationDate;
    private Calendar deadline;

    public Ticket(String name, String description, Priority priority, int cost, Status status, Calendar deadline, Calendar creationDate) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.cost = cost;
        this.status = status;
        this.deadline = deadline;
        this.creationDate = creationDate;
    }

    public Ticket(String name, String description, Priority priority, int cost, Status status, Calendar deadline) {
        this(name, description, priority, cost, status, deadline, Calendar.getInstance());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getCost() {
        return this.cost;
    }

    public Calendar getDeadline() {
        return this.deadline;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setDeadline (Calendar newDeadline) {deadline = newDeadline;}

    public long getWorkableTime() throws IllegalStateException {
        this.dateChecker();

        return this.between(creationDate, deadline);
    }

    public long tillDue() throws IllegalStateException {
        this.dateChecker();

        long tillDue = this.between(Calendar.getInstance(), deadline);
        long daysTillDue = tillDue >= 0 ? tillDue : -1;

        return daysTillDue;
    }

    private void dateChecker() throws IllegalStateException {
        if (this.creationDate.after(deadline)) {
            throw new IllegalStateException("Ticket has a creation date after the deadline");
        } else if (this.deadline == null) {
            throw new NullPointerException("Deadline is null");
        } else if (this.creationDate == null) {
            throw new NullPointerException("Creation date is null");
        }
    }

    public int between(Calendar startDate, Calendar endDate) {
        long startSeconds = startDate.getTimeInMillis();
        long endSeconds = endDate.getTimeInMillis();
        int days = (int) TimeUnit.MILLISECONDS.toDays(Math.abs(endSeconds - startSeconds));

        return days * (startDate.after(endDate) ? -1 : 1);
    }
}