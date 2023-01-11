package comp3350.projectmanagementapplication.objects;

public enum Status {
    TODO("TO-DO"),
    InProgress("In Progress"),
    Complete("Complete");

    private final String string;

    Status(String string) {
        this.string = string;
    }

    public String getValue() {
        return string;
    }

    public static Status from(String string) {
        Status status = null;

        for (Status s : Status.values()) {
            if (s.string.equalsIgnoreCase(string)) {
                status = s;

                break;
            }
        }

        return status;
    }
}