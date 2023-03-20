package container.code.data.repository;

import container.code.data.entity.EmployeeJob;
import container.code.data.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Integer> {
    Optional<Job> findById(Integer id);
    @Query(value = "SELECT j FROM Job j " +
            "WHERE j.isDeleted = FALSE")
    List<Job> findAll();
    @Query(value = "SELECT j " +
            "FROM Job j " +
            "INNER JOIN EmployeeJob ej ON ej.job = j " +
            "INNER JOIN Account ac ON ej.account = ac " +
            "WHERE ac.id = :emp_id AND j.isDeleted = FALSE")
    List<Job> findAllByEmpId(@Param("emp_id") Integer empId);
}
