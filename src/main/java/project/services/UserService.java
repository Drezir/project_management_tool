package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.domain.User;
import project.exceptions.ServerError;
import project.exceptions.ServerException;
import project.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public User saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    try {
      User newUser = userRepository.save(user);
      newUser.setConfirmPassword("*****");
      return newUser;
    } catch (Exception ex) {
      user.setPassword("*****");
      user.setConfirmPassword("*****");
      throw new ServerException("Cannot save or update user", ex)
          .withError(ServerError.USER_CANNOT_PERSIST, "user", user);
    }
  }

}
