package project.security;

import static project.security.SecurityConstants.AUTH_PREFIX;
import static project.security.SecurityConstants.HEADER_STRING;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import project.domain.User;
import project.services.CustomUserDetailsService;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwtToken = extractJwtTokenFromRequest(httpServletRequest);
      if (StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
        Long userId = jwtTokenProvider.extractUserId(jwtToken);
        User user = userDetailsService.loadUserById(userId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception ex) {
      log.error("Could not set user authentication in security context", ex);
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private String extractJwtTokenFromRequest(HttpServletRequest httpServletRequest) {
    String bearerToken = httpServletRequest.getHeader(HEADER_STRING);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTH_PREFIX)) {
      return bearerToken.substring(AUTH_PREFIX.length());
    }
    return null;
  }
}
