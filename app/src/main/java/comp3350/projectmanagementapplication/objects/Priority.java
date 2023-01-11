package comp3350.projectmanagementapplication.objects;

public enum Priority {
    High(3),
    Medium(2),
    Low(1);

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
