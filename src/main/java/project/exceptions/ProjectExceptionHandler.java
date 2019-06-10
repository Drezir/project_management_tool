package project.exceptions;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import project.services.MapValidationErrorService;

@ControllerAdvice
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {

    private final MapValidationErrorService mapValidationErrorService;

    @ExceptionHandler(value = {ServerException.class})
    public final ResponseEntity<?> handleServerException(ServerException ex, WebRequest webRequest) {
        log.error("Exception occured", ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex);
        ex.getServerErrors().forEach(exceptionResponse::addValidationError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleMethodArgumentNotValid(ex.getBindingResult());
    }

    public ResponseEntity<Object> handleMethodArgumentNotValid(BindingResult bindingResult) {
        Map<String, String> fieldErrors = mapValidationErrorService.formatBindingResult(bindingResult);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrors);
    }
}
