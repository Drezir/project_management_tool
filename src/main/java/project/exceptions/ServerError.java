package project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerError {

    public static final ServerError PROJECT_DOES_NOT_EXIST = new ServerError("projectDoesNotExist", "Project ID does not exist");
    public static final ServerError PROJECT_DOES_NOT_CONTAIN_TASK = new ServerError("projectDoesNotContainTask", "Project task does not belong to project.");
    public static final ServerError PROJECT_AUTHENTICATION = new ServerError("projectForbidden", "Project is not in your scope");

    public static final ServerError TASK_DOES_NOT_BELONG_TO_PROJECT = new ServerError("taskDoesNotBelongToProject", "Project task does not belong to project.");

    public static final ServerError USER_NOT_AUTHENTICATED = new ServerError("userNotAuthenticated", "User is not authenticated");
    public static final ServerError USER_DOES_NOT_EXIST = new ServerError("userDoesNotExist", "User does not exist");

    public static ServerError PROJECT_CANNOT_PERSIST(String reason) {
        return new ServerError("projectCannotPersist", reason);
    }
    public static ServerError USER_CANNOT_PERSIST(String reason) {
        return new ServerError("userCannotPersist", reason);
    }
    public static ServerError FIELD_IS_NOT_CORRECT(String reason) {
        return new ServerError("fieldIsNotCorrect", reason);
    }

    private String responseErrorKey;
    private String message;
}
