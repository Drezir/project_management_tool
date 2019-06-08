package project.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {

    public Map<String, String> formatBindingResult(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            errorMap = new HashMap<>();
            for (FieldError err : bindingResult.getFieldErrors()) {
                errorMap.put(err.getField(), err.getDefaultMessage());
            }
        }
        return errorMap;
    }

}
