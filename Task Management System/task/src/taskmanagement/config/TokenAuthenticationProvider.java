package taskmanagement.config;

import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import taskmanagement.entity.Token;
import taskmanagement.entity.TokenAuthentication;
import taskmanagement.entity.User;
import taskmanagement.exception.UsernameNotFoundException;
import taskmanagement.repository.TokenRepository;
import taskmanagement.repository.UserRepository;

import java.time.LocalDateTime;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public TokenAuthenticationProvider(TokenRepository repository,
                                       UserDetailsService userDetailsService,
                                       UserRepository userRepository) {
        this.tokenRepository = repository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Transactional(dontRollbackOn = BadCredentialsException.class)
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var token = authentication.getCredentials().toString();

        Token accessToken = tokenRepository
                .findByToken(token)
                .orElseThrow(() -> new BadCredentialsException("Invalid access token"));

        if (LocalDateTime.now().isAfter(accessToken.getExpiresAt())) {
            tokenRepository.deleteById(accessToken.getId());
            throw new BadCredentialsException("Invalid access token.");
        }

        User user = getUserFromAccessToken(accessToken);

        TokenAuthentication auth = new TokenAuthentication(token);
        auth.setUserDetails(userDetailsService.loadUserByUsername(user.getEmail()));
        auth.setAuthenticated(true);

        return auth;
    }

    private User getUserFromAccessToken(Token token) {
        return userRepository.findById(token.getUserId())
                .orElseThrow(UsernameNotFoundException::new);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}

