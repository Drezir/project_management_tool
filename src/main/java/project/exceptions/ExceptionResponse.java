package project.exceptions;

import lombok.Getter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ExceptionResponse {

    private Map<String, String> errorItems;
    private String stacktrace;

    public ExceptionResponse(Exception ex) {
        errorItems = new HashMap<>();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        printWriter.flush();
        stacktrace = stringWriter.toString();
    }

    public ExceptionResponse with(String value, String message) {
        errorItems.put(value, message);
        return this;
    }

}
