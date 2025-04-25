package taskmanagement.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import taskmanagement.entity.User;
import taskmanagement.entity.UserAdapter;
import taskmanagement.exception.UsernameNotFoundException;
import taskmanagement.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(username.toLowerCase())
                .orElseThrow(UsernameNotFoundException::new);

        return new UserAdapter(user);
    }
}
