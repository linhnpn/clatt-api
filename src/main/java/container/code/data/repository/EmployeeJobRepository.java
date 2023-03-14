package container.code.data.repository;

import container.code.data.entity.EmployeeJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeJobRepository extends JpaRepository<EmployeeJob, Integer> {
//    @Query(value = "SELECT d, d.job.id, d.job.name, d.id, d.account.fullname, d.account.id, d.account.profilePicture, " +
//            " d.account.bio, d.job.price "+
//            "FROM EmployeeJob d LEFT JOIN d.account emp " +
//            "LEFT JOIN emp.bookingOrders bo LEFT JOIN bo.feedback f " +
//            "LEFT JOIN d.job j " +
//            "WHERE j.id = :job_id ")
//    List<EmployeeJob> findAllByJob_Id(@Param("job_id") Integer jobId);

    @Query(value = "SELECT d.id as jobEmpId, j.id as jobId, j.name as jobName, " +
            "d.account.fullname as empName, d.account.id as empId, " +
            "d.account.profilePicture as srcPicture, d.account.bio as description, " +
            "j.price as priceJob, CONCAT(add.description, ', ', dis.name, ', ', pro.name) as location, COUNT(f) as countRate, AVG(f.rate) as averageRate " +
            "FROM EmployeeJob d " +
            "LEFT JOIN d.account emp " +
            "LEFT JOIN emp.address add " +
            "LEFT JOIN add.district dis " +
            "LEFT JOIN dis.province pro " +
            "LEFT JOIN emp.bookingOrders bo " +
            "LEFT JOIN bo.feedback f " +
            "LEFT JOIN d.job j " +
            "WHERE j.id = :job_id " +
            "GROUP BY d.id, j.id")
    List<Map<String, Object>> findAllByJob_Id(@Param("job_id") Integer jobId);


    @Query(value = "SELECT ej " +
            "FROM EmployeeJob ej LEFT JOIN ej.job " +
            "LEFT JOIN ej.account " +
            "WHERE ej.account.id = :account_id " +
            "AND ej.job.id = :job_id")
    EmployeeJob findByJobAndAccountId(@Param("job_id") Integer jobId,@Param("account_id") Integer employeeId);
}
