package comp3350.projectmanagementapplication.objects;

import java.util.ArrayList;

public class Team {
    private final ArrayList<User> users;
    private final ArrayList<Project> projects;
    private String name;

    public Team(String name, ArrayList<User> users, ArrayList<Project> projects) {
        this.name = name;
        this.users = users;
        this.projects = projects;
    }

    public Team(String name) {
        this(name, new ArrayList<>(), new ArrayList<>());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public Project getProject(String name) {
        Project project = null;

        for (Project p : this.projects) {
            if (p.getName().equals(name)) {
                project = p;
                break;
            }
        }

        return project;
    }

    public ArrayList<Project> getProjects() {
        return this.projects;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Team && ((Team) object).getName().equals(this.name);
    }
}