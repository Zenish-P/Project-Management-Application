package comp3350.projectmanagementapplication.persistence;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Team;
import comp3350.projectmanagementapplication.objects.User;

public interface DataAccess {
    Team getTeam(String name);
    ArrayList<Team> getTeams();
    ArrayList<User> getUsers(String teamName);
    Project getProject(String teamName, String name);
    ArrayList<Project> getProjects(String teamName);
    ArrayList<FeatureTicket> getFeatureTickets(String teamName, String projectName);
    void addFeatureTicket(String teamName, String projectName, FeatureTicket featureTicket) throws Exception;
    void removeFeatureTicket(String teamName, String projectName, String ticketName) throws Exception;
    void addProject(String teamName, Project project) throws Exception;
    void addTeam(Team team) throws Exception;
    void addUser(String teamName, User user) throws Exception;
    void removeUser(String teamName, String userName) throws Exception;
}
