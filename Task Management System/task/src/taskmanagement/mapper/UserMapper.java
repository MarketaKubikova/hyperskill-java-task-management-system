package taskmanagement.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import taskmanagement.entity.User;
import taskmanagement.model.UserRequestDto;

@Component
public class UserMapper {
    private final PasswordEncoder encoder;

    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public User toEntity(UserRequestDto dto) {
        return new User(
                dto.email().toLowerCase(),
                encoder.encode(dto.password()));
    }
}
