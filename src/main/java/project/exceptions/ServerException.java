package project.exceptions;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class ServerException extends RuntimeException {

    private Map<String, Collection<ServerExceptionObject>> serverErrors;
    private final boolean printableStacktrace;

    public ServerException(String reason, Throwable throwable) {
        super(reason, throwable);
        serverErrors = new HashMap<>();
        printableStacktrace = throwable != null;
    }

    public ServerException withError(ServerError serverError, String attribute, Object value) {
        if (!serverErrors.containsKey(attribute)) {
            serverErrors.put(attribute, new HashSet<>());
        }
        serverErrors.get(attribute).add(new ServerExceptionObject(serverError.getResponseErrorKey(), serverError.getMessage(), value));
        return this;
    }

    @Data
    public class ServerExceptionObject {
        private final String errorKey;
        private final String message;
        private final Object value;
    }
}
