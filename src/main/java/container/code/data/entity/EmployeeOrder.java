package container.code.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class EmployeeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private BookingOrder bookingOrder;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "employee_id")
    private Account account;
}
