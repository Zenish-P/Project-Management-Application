package comp3350.projectmanagementapplication.persistence;

import android.util.Log;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Priority;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Status;
import comp3350.projectmanagementapplication.objects.Team;
import comp3350.projectmanagementapplication.objects.User;

public final class HSQLDatabase implements DataAccess {
    private interface Constructor<T> {
        T construct(ResultSet sqlQueryResult) throws SQLException;
    }

    public static final String FILE_SYSTEM_DIRECTORY = "database";
    public static final String FILE = "database.script";
    private static final String ADMIN_USER = "SC";
    private static final String ADMIN_PASSWORD = "";

    private static HSQLDatabase instance;

    public static HSQLDatabase getInstance() {
        /*
        Creates / Fetches an instance of the HSQLDatabase
        */

        if (HSQLDatabase.instance == null) {
            HSQLDatabase.instance = new HSQLDatabase();
        }

        return HSQLDatabase.instance;
    }

    private Connection connection;

    public HSQLDatabase() {
        System.out.println("Initializing HSQLDatabase");

        try {
            // Create database connection
            this.connection = DriverManager.getConnection(
                "jdbc:hsqldb:file:/data/user/0/Comp3350.projectmanagementapplication/app_" + HSQLDatabase.FILE_SYSTEM_DIRECTORY + File.separator + HSQLDatabase.FILE.split("\\.")[0],
                HSQLDatabase.ADMIN_USER,
                HSQLDatabase.ADMIN_PASSWORD
            );
        } catch (SQLException exception) {
            System.out.println("Failed to create database");
        }
    }

    public ResultSet execute(String query) throws SQLException {
        return this.connection.createStatement().executeQuery(query);
    }

    @Override
    public ArrayList<FeatureTicket> getFeatureTickets(String teamName, String projectName) {
        return this.get(String.format(
                "SELECT * FROM FeatureTickets WHERE team=%s AND project=%s",
                this.stringToSqlString(teamName),
                this.stringToSqlString(projectName)
        ), this::constructFeatureTicket);
    }

    @Override
    public void removeFeatureTicket (String teamName, String projectName, String ticketName) throws Exception {
        try {
            this.execute(String.format("DELETE FROM FeatureTickets WHERE name='%s' AND team='%s' AND project='%s'", ticketName, teamName, projectName));
            Log.d("DELETE SUCCESSFUL", ticketName);
        } catch (SQLException e) {
            Log.d("Error Message", "ERROR DELETE");
            Log.d("Why Error?", e.getMessage());
        }
    }

    @Override
    public void removeUser(String teamName, String userName) throws Exception {
        this.execute(String.format(
                "DELETE FROM Users WHERE team=%s AND name=%s",
                this.stringToSqlString(teamName),
                this.stringToSqlString(userName))
        );
    }

    @Override
    public Project getProject(String teamName, String name) {
        ArrayList<Project> projects = this.get(String.format(
                "SELECT * FROM Projects WHERE team=%s AND name=%s",
                this.stringToSqlString(teamName),
                this.stringToSqlString(name)
        ), this::constructProject);

       return projects.size() == 0 ? null : projects.get(0);
    }

    @Override
    public ArrayList<Project> getProjects(String teamName) {
        return this.get(String.format(
                "SELECT * FROM Projects WHERE team=%s",
                this.stringToSqlString(teamName)
        ), this::constructProject);
    }

    @Override
    public Team getTeam(String name) {
        ArrayList<Team> teams = this.get(String.format("SELECT * FROM Teams WHERE name=%s", this.stringToSqlString(name)), this::constructTeam);

        return teams.size() == 0 ? null : teams.get(0);
    }

    @Override
    public ArrayList<Team> getTeams() {
        return this.get("SELECT * FROM Teams", this::constructTeam);
    }

    @Override
    public ArrayList<User> getUsers(String teamName) {
        return this.get(String.format("SELECT * FROM Users WHERE team=%s", this.stringToSqlString(teamName)), this::constructUser);
    }

    @Override
    public void addFeatureTicket(String teamName, String projectName, FeatureTicket featureTicket) throws SQLException {

        this.execute(String.format(
                "INSERT INTO FeatureTickets VALUES(%s, %s, %s, %s, %s, %d, %s, %s, %s)",
                this.stringToSqlString(teamName), this.stringToSqlString(projectName),
                this.stringToSqlString(featureTicket.getName()), this.stringToSqlString(featureTicket.getDescription()),
                this.stringToSqlString(featureTicket.getPriority().toString()),
                featureTicket.getCost(), this.stringToSqlString(featureTicket.getStatus().toString()),
                this.stringToSqlString(calendarToDatetime(featureTicket.getDeadline())),
                this.stringToSqlString(calendarToDatetime(featureTicket.getCreationDate())
        )));
    }

    @Override
    public void addProject(String teamName, Project project) throws SQLException {
        this.execute(String.format(
                "INSERT INTO Projects VALUES(%s, %s, %s)",
                this.stringToSqlString(teamName),
                this.stringToSqlString(project.getName()),
                this.stringToSqlString(project.getDescription())
        ));
    }

    @Override
    public void addTeam(Team team) throws SQLException {
        this.execute(String.format(
                "INSERT INTO Teams VALUES(%s)",
                this.stringToSqlString(team.getName()))
        );
    }

    @Override
    public void addUser(String teamName, User user) throws Exception {
        this.execute(String.format(
                "INSERT INTO Users VALUES(%s, %s, %s)",
                this.stringToSqlString(teamName),
                this.stringToSqlString(user.getName()),
                this.stringToSqlString(user.getBio()))
        );
    }

    public void reset() {
        HSQLDatabase.instance = new HSQLDatabase();
    }

    private FeatureTicket constructFeatureTicket(ResultSet sqlQueryResult) throws SQLException {

        return new FeatureTicket(
                sqlQueryResult.getString("name"),
                sqlQueryResult.getString("description"),
                Priority.valueOf(sqlQueryResult.getString("priority")),
                sqlQueryResult.getInt("cost"),
                Status.valueOf(sqlQueryResult.getString("status")),
                datetimeToCalendar(sqlQueryResult.getString("deadline")),
                datetimeToCalendar(sqlQueryResult.getString("creation")));
    }

    private Project constructProject(ResultSet sqlQueryResult) throws SQLException {
        String teamName = sqlQueryResult.getString("team");
        String name = sqlQueryResult.getString("name");

        return new Project(
                name,
                sqlQueryResult.getString("description"),
                this.getFeatureTickets(teamName, name)
        );
    }

    private Team constructTeam(ResultSet sqlQueryResult) throws SQLException {
        String name = sqlQueryResult.getString("name");

        return new Team(
                name,this.getUsers(name), this.getProjects(name)
        );
    }

    private User constructUser(ResultSet sqlQueryResult) throws SQLException {
        String name = sqlQueryResult.getString("name");
        String bio = sqlQueryResult.getString("bio");

        return new User(name, bio);
    }

    private <T> ArrayList<T> get(String sqlQuery, Constructor<T> constructor) {
        /*
        Executes an sqlQuery which returns a result, then iterates through the results to construct the corresponding objects
         */

        ArrayList<T> objects = new ArrayList<>();

        try {
            ResultSet result = this.execute(sqlQuery);

            while (result.next()) {
                objects.add(constructor.construct(result));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return objects;
    }

    private String stringToSqlString(String string) {
        String sqlString = "NULL";

        if (string != null && !string.isEmpty()) {
            sqlString = String.format("'%s'", string);
        }

        return sqlString;
    }

    private String calendarToDatetime(Calendar date) {
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(date.getTime());
    }

    private Calendar datetimeToCalendar(String datetime) {
        Calendar calendar = null;
        String year = "";
        String month = "";
        String day = "";

        if (datetime != null) {
            calendar = Calendar.getInstance();
            year = datetime.substring(0,4);
            if (datetime.charAt(6) == '-') {
                month = datetime.substring(5,6);
                day = datetime.substring(7,9);
            }
            else {
                month = datetime.substring(5, 7);
                day = datetime.substring(8, 10);
            }
            calendar.set(
                    Integer.parseInt(year),
                    Integer.parseInt(month),
                    Integer.parseInt(day));
        }
        return calendar;
    }
}
