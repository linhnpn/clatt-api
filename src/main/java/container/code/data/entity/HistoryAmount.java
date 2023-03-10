package container.code.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class HistoryAmount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "update_amount")
    private Integer updateAmount;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;
}
