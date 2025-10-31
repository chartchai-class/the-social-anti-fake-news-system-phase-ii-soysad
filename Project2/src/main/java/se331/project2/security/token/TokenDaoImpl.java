package se331.project2.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenDaoImpl implements TokenDao {
    final TokenRepository tokenRepository;

    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }
}