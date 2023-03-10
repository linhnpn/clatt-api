package container.code.config;

import container.code.data.entity.RefreshToken;
import container.code.data.repository.AccountRepository;
import container.code.data.repository.RefreshTokenRepository;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Component
public class RefreshTokenProvider {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    CacheManager cacheManager;
//    @Value("${}")
//    long refreshTokenDurationMs;

    @Cacheable("refreshToken")
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAccount(accountRepository.findByEmail(email));

        refreshToken.setExpriryDate(Instant.now().plusMillis(3600000));
        refreshToken.setToken(UUID.randomUUID().toString());

        RefreshToken refreshTokenEncrypt = new RefreshToken(refreshToken);
        refreshTokenEncrypt.setToken(DigestUtils.sha3_256Hex(refreshToken.getToken()));

        cacheManager.getCache("refreshToken").put(DigestUtils.sha3_256Hex(refreshToken.getToken()), refreshToken );

        refreshTokenRepository.save(refreshTokenEncrypt);
        return refreshToken;
    }


//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpriryDate().compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(token);
//            throw new RefreshTokenException("Refresh token was expired");
//        }
//        return token;
//    }

    @Transactional
    public RefreshToken deleteByUserId(Integer accountId) {
        RefreshToken refreshToken = refreshTokenRepository.findByAccount_Id(accountId).orElseThrow(() -> new RuntimeException("hi"));
        refreshTokenRepository.deleteAllByAccount_Id(accountId);
        return refreshToken;
    }

    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
