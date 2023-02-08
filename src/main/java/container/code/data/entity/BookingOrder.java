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
    @JoinColumn(name = "user_id")
    private Account account;

    @Column(name = "status")
    private String status;

    @Column(name = "work_time")
    private Integer workTime;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "timestamp")
    private Date timestamp;

    @OneToMany(mappedBy = "bookingOrder")
    private List<EmployeeOrder> employeeOrders;

    @OneToMany(mappedBy = "bookingOrder")
    private List<OrderJob> orderJobs;
}
