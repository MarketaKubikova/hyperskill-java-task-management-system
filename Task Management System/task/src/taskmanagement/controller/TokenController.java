package taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taskmanagement.model.TokenResponseDto;
import taskmanagement.service.TokenService;

@RestController
@RequestMapping("/api/auth")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponseDto> requestToken(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(tokenService.requestToken(userDetails));
    }
}
