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
public class EmployeeJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "employee_id")
    private Account account;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "job_id")
    private Job job;
}
