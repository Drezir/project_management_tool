package project.security;

public class SecurityConstants {

  public static final String SIGNUP_URL = "/api/users/**";
  public static final String H2_URL = "/h2-console/**";
  public static final String SECRET = "SecretKeyToGenerateJWTs";
  public static final String AUTH_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final long TOKEN_EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes

}
