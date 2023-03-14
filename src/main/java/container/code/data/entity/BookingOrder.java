package container.code.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class BookingOrder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "renter_id")
    private Account renter;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "employee_id")
    private Account employee;

    @Column(name = "status")
    private String status;

    @Column(name = "work_hour")
    private Integer workHour;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "work_date")
    private LocalDateTime workDate;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "bookingOrder")
    @JsonIgnore
    private List<OrderJob> orderJobs;

    @OneToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;
}
