package project.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.domain.User;
import project.exceptions.ServerError;
import project.exceptions.ServerException;
import project.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new ServerException("User not found", null)
          .withError(ServerError.USER_DOES_NOT_EXIST, "username", username);
    }
    return user;
  }

  @Transactional
  public User loadUserById(Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElseThrow(() ->
        new ServerException("User not found", null)
            .withError(ServerError.USER_DOES_NOT_EXIST, "id", id)
    );
  }
}
