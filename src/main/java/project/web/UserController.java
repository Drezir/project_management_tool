package project.web;

import static project.security.SecurityConstants.AUTH_PREFIX;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.User;
import project.exceptions.ProjectExceptionHandler;
import project.payload.JWTLoginSuccessResponse;
import project.payload.LoginRequest;
import project.security.JwtTokenProvider;
import project.services.UserService;
import project.validator.UserValidator;

@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserValidator userValidator;
  private final ProjectExceptionHandler projectExceptionHandler;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  @PostMapping(path = "/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
    userValidator.validate(user, bindingResult);
    if (bindingResult.hasErrors()) {
      return projectExceptionHandler.handleMethodArgumentNotValid(bindingResult);
    }

    User createdUser = userService.saveUser(user);
    return ResponseEntity.ok(createdUser);
  }

  @PostMapping(path = "/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwtToken = AUTH_PREFIX + jwtTokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwtToken));
  }

}
