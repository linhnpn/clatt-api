package container.code.data.entity;

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

    @Column(name = "measure_value")
    private Integer measureValue;

    @Column(name = "measure_unit")
    private String measureUnit;

    @Column(name = "thumbnail_job_image")
    private String thumbnailJobImage;

    @OneToMany(mappedBy = "job")
    private List<OrderJob> orderJobs;

    @OneToMany(mappedBy = "job")
    private List<SkillJob> skillJobs;
}
