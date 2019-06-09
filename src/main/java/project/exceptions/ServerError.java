package project.exceptions;


public enum ServerError {

    PROJECT_CANNOT_PERSIST("projectCannotPersist", "Cannot save or update project"),
    PROJECT_DOES_NOT_EXIST("projectDoesNotExist", "Project ID does not exist"),
    PROJECT_DOES_NOT_CONTAIN_TASK("projectDoesNotContainTask", "Project task does not belong to project."),

    TASK_DOES_NOT_BELONG_TO_PROJECT("taskDoesNotBelongToProject", "Project task does not belong to project.");

    private String message;
    private String responseErrorKey;

    ServerError(String responseErrorKey, String message){
        this.message = message;
        this.responseErrorKey = responseErrorKey;
    }

    public String getMessage() {
        return message;
    }

    public String getResponseErrorKey() {
        return responseErrorKey;
    }
}
