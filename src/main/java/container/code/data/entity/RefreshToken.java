package container.code.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String token;

    private Instant expriryDate;

    public RefreshToken(RefreshToken refreshToken) {
        this.id = refreshToken.getId();
        this.account = refreshToken.account;
        this.token = refreshToken.getToken();
        this.expriryDate = refreshToken.getExpriryDate();
    }

}
