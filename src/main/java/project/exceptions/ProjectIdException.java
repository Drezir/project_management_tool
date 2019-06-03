package project.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException {

    @Getter
    private final String projectIdentifier;

    public ProjectIdException(String message, String projectIdentifier) {
        super(message);
        this.projectIdentifier = projectIdentifier;
    }
}
