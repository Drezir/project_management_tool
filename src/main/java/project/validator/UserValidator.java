package project.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.domain.User;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return User.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    User user = (User)o;

    String userPassword = user.getPassword();
    String userConfirmPassword = user.getConfirmPassword();

    if (userPassword != null && userPassword.length() < 6) {
      errors.rejectValue("password", "Length", "Password should be at least 6 characters long");
    }
    if (StringUtils.hasText(userPassword) && StringUtils.hasText(userConfirmPassword)) {
      if (! userPassword.equals(userConfirmPassword)) {
        errors.rejectValue("confirmPassword", "Match", "Passwords must match");
      }
    } else if (!StringUtils.hasText(userConfirmPassword)) {
      errors.rejectValue("confirmPassword", "Match", "Passwords must match");
    }
  }
}
