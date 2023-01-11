package comp3350.projectmanagementapplication.objects;

public class User {
    private String name;
    private String bio;

    public User(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() { return this.bio; }

    @Override
    public boolean equals(Object object) {
        return object instanceof User ? ((User) object).getName().equals(this.name) : false;
    }
}
