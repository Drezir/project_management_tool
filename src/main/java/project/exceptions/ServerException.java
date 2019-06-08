package project.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServerException extends RuntimeException {

    @Getter
    Map<String, Object> awareObjects;

    public ServerException(String reason, Throwable throwable) {
        super(reason, throwable);
        awareObjects = new HashMap<>();
    }

    public ServerException awareObject(String name, Object value) {
        awareObjects.put(name, value);
        return this;
    }

}
