package container.code.data.repository;

import container.code.data.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query(value = "SELECT f, f.bookingOrder.renter.id, f.bookingOrder.renter.fullname, f.bookingOrder.employee.id " +
            "FROM Feedback f  LEFT JOIN f.bookingOrder bo " +
            "LEFT JOIN bo.renter us LEFT JOIN bo.employee emp " +
            "LEFT JOIN bo.orderJobs oj LEFT JOIN oj.job " +
            "WHERE emp.id = :employee_id AND oj.job.id = :job_id " +
            "AND (:rate IS NULL OR f.rate = :rate)")
    List<Feedback> findAllByEmployeeOrder(@Param("employee_id") int employeeId, @Param("job_id") int jobId, @Param("rate") Integer rate);


    @Query(value = "SELECT COUNT(*) FROM Feedback f LEFT JOIN f.bookingOrder bo " +
            "LEFT JOIN bo.orderJobs oj LEFT JOIN oj.job j LEFT JOIN bo.employee emp " +
            "WHERE j.id = :job_id AND emp.id = :emp_id")
    Long countFeedback(@Param("job_id") Integer jobId, @Param("emp_id") Integer empId);

    @Query(value = "SELECT COALESCE(AVG(f.rate), 5) FROM Feedback f LEFT JOIN f.bookingOrder bo " +
            "LEFT JOIN bo.orderJobs oj LEFT JOIN oj.job j LEFT JOIN bo.employee emp " +
            "WHERE j.id = :job_id AND emp.id = :emp_id ")
    Double averageFeedback(@Param("job_id") Integer jobId, @Param("emp_id") Integer empId);
}
