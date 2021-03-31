package engine.user;

import engine.exception.UsernameAlreadyTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements UserDetailsService {

    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        if (userRepository.existsById(user.getEmail())) {
            throw new UsernameAlreadyTakenException("Email already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email + " not found");
        }
        return new org.springframework.security.core.userdetails.
                User(user.getEmail(), user.getPassword(), List.of());
    }
}
