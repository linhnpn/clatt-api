package container.code.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
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
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

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

    @Column(name = "available_hire")
    private Byte availableHire;

    @OneToMany(mappedBy = "account")
    private List<Address> addresses;

    @OneToMany(mappedBy = "account")
    private List<HistoryAmount> historyAmounts;

    @OneToMany(mappedBy = "account")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "account")
    private List<EmployeeOrder> employeeOrders;

    @OneToMany(mappedBy = "account")
    private List<SkillEmployee> skillEmployees;

    @OneToMany(mappedBy = "account")
    private List<BookingOrder> bookingOrders;
}
