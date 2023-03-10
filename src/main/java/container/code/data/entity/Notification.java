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

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Notification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "detail")
    private String detail;
    @Column(name = "status")
    private String status;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "notification_status_id")
    private NotificationStatus notificationStatus;
}
