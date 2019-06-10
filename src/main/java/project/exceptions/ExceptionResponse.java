package project.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import project.exceptions.ServerException.ServerExceptionObject;

@Getter
public class ExceptionResponse {

    private Map<String, Object> errorItems;
    private String stacktrace;

    public ExceptionResponse(Exception ex) {
        errorItems = new HashMap<>();

        if (ex != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            ex.printStackTrace(printWriter);
            printWriter.flush();
            stacktrace = stringWriter.toString();
        }
    }

    public ExceptionResponse addValidationError(ServerError key, Set<ServerExceptionObject> value) {
        errorItems.put(key.getResponseErrorKey(), value);
        return this;
    }

}
