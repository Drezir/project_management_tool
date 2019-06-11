package project.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import project.exceptions.ServerException.ServerExceptionObject;

@Getter
public class ExceptionResponse {

    private Map<String, Object> errorItems;
    @JsonInclude(Include.NON_NULL)
    private String stacktrace;

    public ExceptionResponse(ServerException ex) {
        errorItems = new HashMap<>();

        if (ex.isPrintableStacktrace()) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            ex.printStackTrace(printWriter);
            printWriter.flush();
            stacktrace = stringWriter.toString();
        }
    }

    public ExceptionResponse addValidationError(String attribute, Collection<ServerExceptionObject> attributeErrors) {
        errorItems.put(attribute, attributeErrors);
        return this;
    }

}
