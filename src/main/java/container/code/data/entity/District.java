package container.code.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class District {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "province_id")
    @JsonIgnoreProperties("districts")
    private Province province;

    @OneToMany(mappedBy = "district")
    @JsonIgnoreProperties("district")
    private List<Address> addresses;

}
