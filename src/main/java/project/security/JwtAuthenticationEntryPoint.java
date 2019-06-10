package project.security;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import project.exceptions.ExceptionResponse;
import project.exceptions.ServerError;
import project.exceptions.ServerException;

/**
 * Used when user is not authenticated - exception thrown
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
    ServerException serverException = new ServerException("Invalid user or password", e)
        .withError(ServerError.USER_NOT_AUTHENTICATED, "username", "Invalid username")
        .withError(ServerError.USER_NOT_AUTHENTICATED, "password", "Invalid password");

    ExceptionResponse exceptionResponse = new ExceptionResponse(serverException);
    serverException.getServerErrors().forEach(exceptionResponse::addValidationError);

    String jsonServerException = new Gson().toJson(exceptionResponse);

    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    httpServletResponse.getWriter().print(jsonServerException);
  }
}
