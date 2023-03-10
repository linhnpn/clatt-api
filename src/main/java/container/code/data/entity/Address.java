package container.code.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name ="account_id")
    private int accountId;
    @Column(name = "district_id")
    private int districtId;
    @ManyToOne
    @JsonIgnoreProperties("addresses")
    @JoinColumn(name = "account_id",insertable = false, updatable = false)
    private Account account;
    @ManyToOne
    @JsonIgnoreProperties("addresses")
    @JoinColumn(name = "district_id",insertable = false, updatable = false)
    private District district;
    @Column(name = "description")
    private String description;
}
