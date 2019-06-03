package project.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MapValidationErrorService {

    public Optional<Map<String, String>> formatBindingResult(BindingResult bindingResult) {
        Map<String, String> errorMap = null;
        if (bindingResult.hasErrors()) {
            errorMap = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        }
        return Optional.ofNullable(errorMap);
    }

}
