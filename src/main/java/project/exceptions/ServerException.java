package project.exceptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServerException extends RuntimeException {

    @Getter
    private Map<ServerError, Set<ServerExceptionObject>> serverErrors;

    public ServerException(String reason, Throwable throwable) {
        super(reason, throwable);
        serverErrors = new HashMap<>();
    }

    public ServerException withError(ServerError serverError, String attribute, Object value) {
        if (!serverErrors.containsKey(serverError)) {
            serverErrors.put(serverError, new HashSet<>());
        }
        serverErrors.get(serverError).add(new ServerExceptionObject(serverError.getMessage(), attribute, value));
        return this;
    }

    @Data
    public class ServerExceptionObject {
        private final String message;
        private final String attribute;
        private final Object value;
    }
}
