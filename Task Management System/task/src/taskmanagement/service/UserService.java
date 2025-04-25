package taskmanagement.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskmanagement.entity.User;
import taskmanagement.exception.UserAlreadyExistsException;
import taskmanagement.mapper.UserMapper;
import taskmanagement.model.UserRequestDto;
import taskmanagement.repository.UserRepository;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void registerUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        userRepository.save(user);
    }
}
