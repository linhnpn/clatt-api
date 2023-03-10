package container.code.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Feedback {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "detail")
    private String detail;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @OneToOne(mappedBy = "feedback")
    private BookingOrder bookingOrder;
}
