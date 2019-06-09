package project.exceptions;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServerException extends RuntimeException {

    @Getter
    private Map<ServerError, ServerExceptionObject> serverErrors;

    public ServerException(String reason, Throwable throwable) {
        super(reason, throwable);
        serverErrors = new HashMap<>();
    }

    public ServerException withError(ServerError serverError, String attribute, Object value) {
        serverErrors.put(serverError, new ServerExceptionObject(serverError.getMessage(), attribute, value));
        return this;
    }

    @Data
    public class ServerExceptionObject {
        private final String message;
        private final String attribute;
        private final Object value;
    }
}
