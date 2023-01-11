package comp3350.projectmanagementapplication.business;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.App;
import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Team;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.persistence.DataAccess;

public class DatabaseAccessor {
    private interface Adder {
        void add() throws Exception;
    }

    private DataAccess database;

    public DatabaseAccessor(DataAccess database) {
        this.database = database;
    }

    public DatabaseAccessor() {
        this(App.getDatabase());
    }

    public Team getTeam(String name) {
        return this.database.getTeam(name);
    }

    public ArrayList<Team> getTeams() {
        return this.database.getTeams();
    }

    public ArrayList<User> getUsers(String teamName) {
        return this.database.getUsers(teamName);
    }

    public Project getProject(String teamName, String name) {
        return this.database.getProject(teamName, name);
    }

    public ArrayList<Project> getProjects(String teamName) {
        return this.database.getProjects(teamName);
    }

    public ArrayList<FeatureTicket> getFeatureTickets(String teamName, String projectName) {
        return this.database.getFeatureTickets(teamName, projectName);
    }

    public ArrayList<User> getUserList(String teamName) {
        return this.database.getUsers(teamName);
    }

    public boolean addTeam(Team team) {
        return this.add(() -> this.database.addTeam(team));
    }

    public boolean addProject(String teamName, Project project) {
        return this.add(() -> this.database.addProject(teamName, project));
    }

    public boolean addUser(String teamName, User user) {
        return this.add(() -> this.database.addUser(teamName, user));
    }

    public boolean addFeatureTicket(String teamName, String projectName, FeatureTicket featureTicket) {
        return this.add(() -> this.database.addFeatureTicket(teamName, projectName, featureTicket));
    }

    public boolean updateUser(String teamName, String name, User newUser) {
        return this.add(() -> {
            this.database.removeUser(teamName, name);
            this.database.addUser(teamName, newUser);
        });
    }

    public boolean removeUser(String teamName, String name){
        return this.add(() -> this.database.removeUser(teamName, name));
    }

    public boolean updateFeatureTicket (String teamName, String projectName, String toBeRemoved, FeatureTicket newTicket) {
        boolean isRemoved = removeFeatureTicket(teamName, projectName, toBeRemoved);
        if (isRemoved) {
            addFeatureTicket(teamName, projectName, newTicket);
        }
        return isRemoved;
    }

    public boolean removeFeatureTicket (String teamName, String projectName, String toBeRemoved) {
        return this.add(() -> {
            this.database.removeFeatureTicket(teamName, projectName, toBeRemoved);
        });
    }
    
    private boolean add(Adder addFunction) {
        boolean success = true;

        try {
            addFunction.add();
        } catch (Exception exception) {
            success = false;
        }

        return success;
    }
}
