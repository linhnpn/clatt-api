package container.code.data.repository;

import container.code.data.entity.District;
import container.code.data.entity.EmployeeJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeJobRepository extends JpaRepository<EmployeeJob, Integer> {
    @Query(value = "SELECT d, d.job.id, d.job.name, d.id, d.account.fullname, d.account.id, d.account.profilePicture, " +
            " d.account.bio, d.job.price "+
            "FROM EmployeeJob d LEFT JOIN d.account emp " +
            "LEFT JOIN d.job j " +
            "WHERE j.id = :job_id ")
    List<EmployeeJob> findAllByJob_Id(@Param("job_id") Integer jobId);

}
