package project.exceptions;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServerException.class})
    public final ResponseEntity<?> handleServerException(ServerException ex, WebRequest webRequest) {
        log.error("Exception occured", ex);
        ExceptionResponse exceptionResponse = generateFromServerException(ex);
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

    public ExceptionResponse generateFromServerException(ServerException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex);
        ex.getServerErrors().forEach(exceptionResponse::addValidationError);
        return exceptionResponse;
    }

    public ResponseEntity<Object> handleMethodArgumentNotValid(BindingResult bindingResult) {
        Collection<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ServerException serverException = new ServerException("Invalid fields", null);
        fieldErrors.forEach(fieldError -> {
                serverException.withError(
                    ServerError.FIELD_IS_NOT_CORRECT(fieldError.getDefaultMessage()),
                    fieldError.getField(),
                    fieldError.getRejectedValue());
            });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generateFromServerException(serverException));
    }
}
