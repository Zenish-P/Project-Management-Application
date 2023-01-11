package comp3350.projectmanagementapplication.objects;

import java.util.Calendar;

public class Iteration {
    static private int iterationIDTracker = 0;
    private final int iterationID;
    private Project project;
    private Calendar start;
    private Calendar end;

    public Iteration(Project project, Calendar start, Calendar end) {
        this.project = project;
        this.start = start;
        this.end = start != null && end != null && start.compareTo(end) > 0 ? start : end;
        this.iterationID = Iteration.iterationIDTracker++;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Calendar getStart() {
        return this.start;
    }

    public boolean setStart(Calendar start) {
        boolean set = false;

        if (this.end != null && this.end.compareTo(start) >= 0) {
            this.start = start;
            set = true;
        }

        return set;
    }

    public Calendar getEnd() {
        return this.end;
    }

    public boolean setEnd(Calendar end) {
        boolean set = false;

        if (this.start != null && this.start.compareTo(end) <= 0) {
            this.end = end;
            set = true;
        }

        return set;
    }

    public int getID() {
        return this.iterationID;
    }
}
