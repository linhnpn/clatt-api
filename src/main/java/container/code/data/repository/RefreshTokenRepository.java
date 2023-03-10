package container.code.data.repository;

import container.code.data.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByAccount_Id(Integer id);

    @Query("DELETE FROM RefreshToken t WHERE t.account.id = ?1")
    void deleteAllByAccount_Id(Integer account_id);

    @Query("delete from RefreshToken r where r.token = ?1")
    void deleteByToken(String token);
}
