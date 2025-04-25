package taskmanagement.model;

public enum Status {
    CREATED("CREATED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status valueOfString(String value) {
        for (Status e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}


