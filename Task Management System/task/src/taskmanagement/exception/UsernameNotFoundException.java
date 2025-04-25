package taskmanagement.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException() {
        super("User not found");
    }
}
