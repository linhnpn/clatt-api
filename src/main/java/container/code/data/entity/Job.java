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
public class Job {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "measure_unit")
    private String measureUnit;

    @Column(name = "thumbnail_job_image")
    private String thumbnailJobImage;

    @OneToMany(mappedBy = "job")
    @JsonIgnore
    private List<EmployeeJob> employeeJobs;

    @OneToMany(mappedBy = "job")
    @JsonIgnore
    private List<OrderJob> orderJobs;

}
