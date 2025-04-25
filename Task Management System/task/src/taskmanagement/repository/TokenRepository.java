package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.entity.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
