package comp3350.projectmanagementapplication.objects;

import java.util.ArrayList;

public class Project {
    private final ArrayList<FeatureTicket> featureTickets;
    private String name;
    private String description;

    public Project(String name, String description, ArrayList<FeatureTicket> featureTickets) {
        this.name = name;
        this.description = description;
        this.featureTickets = featureTickets;
    }

    public Project(String name, String description) {
        this(name, description, new ArrayList<>());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<FeatureTicket> getFeatureTickets() {
        return this.featureTickets;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Project && ((Project) object).getName().equals(this.name);
    }
}
