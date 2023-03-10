package container.code.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Account {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "role")
    private String role;
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "fcm_token")
    private String fcmToken;

    @OneToOne(mappedBy = "account")
    @JsonIgnoreProperties("account")
    private Address address;

    @OneToMany(mappedBy = "account")
    @JsonIgnoreProperties("account")
    @JsonIgnore
    private List<EmployeeJob> employeeJobs;

    @OneToMany(mappedBy = "account")
    @JsonIgnoreProperties("account")
    @JsonIgnore
    private List<HistoryAmount> historyAmounts;

    @OneToMany(mappedBy = "account")
    @JsonIgnoreProperties({"account", "notifications", "bookingOrders"})
    @JsonIgnore
    private List<Notification> notifications;

    @OneToMany(mappedBy = "renter")
    @JsonIgnoreProperties({"account", "notifications", "bookingOrders"})
    @JsonIgnore
    private List<BookingOrder> bookingOrders;

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties("account")
    @JsonIgnore
    private List<BookingOrder> bookedOrders;
}
