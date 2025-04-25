package taskmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import taskmanagement.entity.Token;
import taskmanagement.entity.User;
import taskmanagement.exception.UsernameNotFoundException;
import taskmanagement.model.TokenResponseDto;
import taskmanagement.repository.TokenRepository;
import taskmanagement.repository.UserRepository;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public TokenResponseDto requestToken(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UsernameNotFoundException::new);

        Token token = new Token(user.getId());
        tokenRepository.save(token);

        return new TokenResponseDto(token.getToken());
    }
}
