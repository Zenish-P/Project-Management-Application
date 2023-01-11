package comp3350.projectmanagementapplication.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Priority;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Status;
import comp3350.projectmanagementapplication.objects.Team;
import comp3350.projectmanagementapplication.objects.User;

public class StubDatabase implements DataAccess {
    private static StubDatabase instance;

    private ArrayList<Team> teams;

    public static StubDatabase getInstance() {
        if (StubDatabase.instance == null) {
            StubDatabase.instance = new StubDatabase();
        }

        return StubDatabase.instance;
    }

    public StubDatabase() {
        System.out.println("Initializing StubDatabase");

        this.initialize();
    }

    public void initialize() {
        Calendar date = Calendar.getInstance();
        date.set(2022, 7, 20);

        this.teams = new ArrayList<>();

        // Team #1
        this.teams.add(
                new Team(
                        "Team #1",
                        new ArrayList<>(Arrays.asList(
                                new User("Graydon", "Likes pizza"),
                                new User("Guido", "Likes sushi"),
                                new User("Brendan", "Likes salad"),
                                new User("Dennis", "Likes steak")
                        )),
                        new ArrayList<>(Arrays.asList(
                                new Project(
                                        "Website",
                                        "A website for selling company products",
                                        new ArrayList<>(Arrays.asList(
                                                new FeatureTicket(
                                                        "Center title element on homepage",
                                                        "Center the title element on the homepage",
                                                        Priority.Low, 1, Status.Complete, date
                                                ),
                                                new FeatureTicket(
                                                        "Add company logo to homepage header",
                                                        "Add the company logo on the header on the homepage",
                                                        Priority.Medium, 1, Status.InProgress, date
                                                ),
                                                new FeatureTicket(
                                                        "Create a product page",
                                                        "Create a product page allowing users to view and purchase products",
                                                        Priority.High, 4, Status.Complete, null
                                                ),
                                                new FeatureTicket(
                                                        "Add a footer to the homepage",
                                                        "Add a footer element to the homepage, listing T&C details and copyright information",
                                                        Priority.Low, 2, Status.TODO, null
                                                ),
                                                new FeatureTicket(
                                                        "Add a company info page",
                                                        "Add a page which lists company information and vision",
                                                        Priority.Medium, 3, Status.TODO, null
                                                )
                                        ))
                                ),
                                new Project(
                                        "iOS App",
                                        "An app for selling company products",
                                        new ArrayList<>(Arrays.asList(
                                                new FeatureTicket(
                                                        "Adjust the default theme's primary colour",
                                                        "Adjust the primary colour of the default theme to be darker",
                                                        Priority.Low, 1, Status.Complete, date
                                                ),
                                                new FeatureTicket(
                                                        "Implement notifications for item discounts",
                                                        "Have the app notify the user of product discounts",
                                                        Priority.Medium, 2, Status.InProgress, null
                                                ),
                                                new FeatureTicket(
                                                        "Create card elements for displaying product information",
                                                        "Create card elements which list the name, description and price of a product",
                                                        Priority.High, 3, Status.Complete, null
                                                ),
                                                new FeatureTicket(
                                                        "Fix navigation bug on back-button press",
                                                        "Fix the navigation bug which occurs when users press the back-button in the product listing section",
                                                        Priority.Medium, 2, Status.TODO, null
                                                ),
                                                new FeatureTicket(
                                                        "Add a splash screen",
                                                        "Add a splash screen containing the company's name and logo which is displayed on startup",
                                                        Priority.Low, 1, Status.TODO, null
                                                )
                                        ))
                                )
                        ))
                )
        );

        // Team #2
        this.teams.add(
                new Team(
                        "Team #2",
                        new ArrayList<>(Arrays.asList(
                                new User("Leonardo", "Likes dogs"),
                                new User("Pablo", "Likes cats"),
                                new User("Salvador", "Likes parrots"),
                                new User("Vincent", "Likes gorillas")
                        )),
                        new ArrayList<>(Arrays.asList(
                                new Project(
                                        "Role-playing Game",
                                        "An RPG game",
                                        new ArrayList<>(Arrays.asList(
                                                new FeatureTicket(
                                                        "Implement a basic quest system for NPCs",
                                                        "Allow NPCs to give players quests",
                                                        Priority.High, 4, Status.Complete, date
                                                ),
                                                new FeatureTicket(
                                                        "Implement an equipment system for players",
                                                        "Allow players to equip certain items",
                                                        Priority.Medium, 3, Status.InProgress, date
                                                ),
                                                new FeatureTicket(
                                                        "Implement auto-sorting for player inventories",
                                                        "Allow for user's to auto-sort their inventories",
                                                        Priority.Low, 2, Status.TODO, null
                                                ),
                                                new FeatureTicket(
                                                        "Implement a combat system",
                                                        "Allow for mobs to attack each other with weapons",
                                                        Priority.High, 4, Status.TODO, null
                                                )
                                        ))
                                ),

                                new Project(
                                        "Platforming Game",
                                        "A platforming game",
                                        new ArrayList<>(Arrays.asList(
                                                new FeatureTicket(
                                                        "Implement a movement system",
                                                        "Allow for the player and enemies to move",
                                                        Priority.High, 3, Status.Complete, date
                                                ),
                                                new FeatureTicket(
                                                        "Add obstacles which eliminate the player on collision",
                                                        "Add obstacles to various levels which eliminate the player on collision",
                                                        Priority.Medium, 3, Status.InProgress, date
                                                ),
                                                new FeatureTicket(
                                                        "Add a hot-key which restarts the current level",
                                                        "Add a hot-key which the player can use to restart the current level",
                                                        Priority.Low, 1, Status.TODO, null
                                                ),
                                                new FeatureTicket(
                                                        "Allow for enemies to be damaged",
                                                        "Allow for enemies to be damaged when the player jumps on them",
                                                        Priority.High, 2, Status.Complete, null
                                                )
                                        ))
                                )
                        ))
                )
        );
    }

    @Override
    public Team getTeam(String name) {
        Team team = null;

        for (Team t : this.teams) {
            if (t.getName().equals(name)) {
                team = t;
                break;
            }
        }

        return team;
    }

    @Override
    public ArrayList<Team> getTeams() {
        return this.teams;
    }

    @Override
    public ArrayList<User> getUsers(String teamName) {
        Team team = this.getTeam(teamName);

        return team != null ? team.getUsers() : null;
    }

    @Override
    public Project getProject(String teamName, String name) {
        Team team = this.getTeam(teamName);

        return team != null ? team.getProject(name) : null;
    }

    @Override
    public ArrayList<Project> getProjects(String teamName) {
        Team team = this.getTeam(teamName);

        return team != null ? team.getProjects() : null;
    }

    @Override
    public ArrayList<FeatureTicket> getFeatureTickets(String teamName, String projectName) {
        Project project = this.getProject(teamName, projectName);

        return project != null ? project.getFeatureTickets() : null;
    }

    @Override
    public void addFeatureTicket(String teamName, String projectName, FeatureTicket featureTicket) throws Exception {
        this.addUnique(featureTicket, this.getFeatureTickets(teamName, projectName));
    }

    @Override
    public void removeFeatureTicket(String teamName, String projectName, String ticketName) throws Exception {
        ArrayList<FeatureTicket> tickets = this.getProject(teamName, projectName).getFeatureTickets();
        tickets.remove(new FeatureTicket(ticketName, null, Priority.Low, 0, Status.TODO, null));
    }

    @Override
    public void addProject(String teamName, Project project) throws Exception {
        this.addUnique(project, this.getProjects(teamName));
    }

    @Override
    public void addUser(String teamName, User user) throws Exception {
        Team team = this.getTeam(teamName);

        if (team != null) {
            this.addUnique(user, team.getUsers());
        }
    }

    @Override
    public void addTeam(Team team) throws Exception {
        this.addUnique(team, this.teams);
    }

    @Override
    public void removeUser(String teamName, String name) {
        Team team = this.getTeam(teamName);

        if (team != null) {
            team.getUsers().remove(new User(name, null));
        }
    }

    public void reset() {
        this.initialize();
    }

    private <T> void addUnique(T object, ArrayList<T> list) throws Exception {
        if (object != null && list != null) {
            if (list.contains(object)) {
                throw new Exception("Duplicate");
            } else {
                list.add(object);
            }
        }
    }
}