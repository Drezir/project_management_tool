package project.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import project.services.MapValidationErrorService;

import java.util.Map;

@ControllerAdvice
@RestController
@RequiredArgsConstructor
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {

    private final MapValidationErrorService mapValidationErrorService;

    @ExceptionHandler(value = {ServerException.class})
    public final ResponseEntity<?> handleServerException(ServerException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex);
        ex.getAwareObjects().forEach(exceptionResponse::addValidationError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        Map<String, String> fieldErrors = mapValidationErrorService.formatBindingResult(ex.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrors);
    }
}
