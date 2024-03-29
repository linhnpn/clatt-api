package container.code.data.repository;

import container.code.data.entity.BookingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookingOrderRepository extends JpaRepository<BookingOrder, Integer> {

    @Query("SELECT t FROM BookingOrder t WHERE t.employee.id = ?1")
    List<BookingOrder> findByEmployeeId(Integer id);

    @Query("SELECT t FROM BookingOrder t WHERE t.employee.id = ?1 and t.feedback.rate = ?2")
    List<BookingOrder> findByEmployeeIdAndRate(Integer id, Integer rate);

    @Query("SELECT t FROM BookingOrder t WHERE t.renter.id = ?1")
    List<BookingOrder> findByRenterId(Integer id);

    @Query("SELECT t.status FROM BookingOrder t WHERE t.id = ?1")
    String findStatusById(Integer id);

    @Modifying
    @Query("UPDATE BookingOrder t SET t.status = ?1 WHERE t.id = ?1")
    void updateStatus(Integer id, String status);

    @Query("SELECT t FROM BookingOrder t WHERE t.employee.id = ?1 and t.status = ?2")
    List<BookingOrder> findByEmployeeIdAndStatus(Integer id, String status);

    @Query(value =
            "SELECT bo.id as id, " +
                    "ren.id as user_id, ren.fullname as user_name, e.id as emp_id, e.fullname as emp_name, " +
                    "bo.status as status, bo.workHour as workTime, bo.timestamp as timestamp, bo.location as location, " +
                    "j.id as job_id, j.name as job_name, bo.description as description, j.thumbnailJobImage as jobImage, j.price as price " +
                    "FROM BookingOrder bo " +
                    "INNER JOIN bo.employee e " +
                    "INNER JOIN bo.renter ren " +
                    "INNER JOIN bo.orderJobs oj " +
                    "INNER JOIN oj.job j " +
                    "WHERE (:status IS NULL OR bo.status = :status) " +
                    "AND (:bookingId IS NULL OR bo.id = :bookingId)" +
                    "AND (:userId IS NULL OR ren.id = :userId) " +
                    "AND (:employeeId IS NULL OR e.id = :employeeId) " +
                    "ORDER BY bo.timestamp DESC", nativeQuery = false)
    List<Map<String, Object>> findAllByStatusId(@Param("status") String status, @Param("userId") Integer userId,
                                                @Param("employeeId") Integer employeeId, @Param("bookingId") Integer bookingId);

    @Query(value = "SELECT bo " +
            "FROM BookingOrder bo LEFT JOIN bo.employee emp " +
            "WHERE bo.workDate >= :start " +
            "AND emp.id = :id " +
            "AND bo.status = 'undone'")
    List<BookingOrder> findByDateTime(@Param("start") LocalDateTime start, @Param("id") Integer id);

    @Query(value =
            "SELECT MONTH(bo.workDate) AS month, " +
                    "COUNT(bo.id) as count, COALESCE(SUM(j.price * bo.workHour), 0) as sumPrice " +
                    "FROM BookingOrder bo " +
                    "LEFT JOIN bo.orderJobs oj " +
                    "LEFT JOIN oj.job j " +
                    "WHERE bo.status = :status " +
                    "AND YEAR(bo.workDate) = YEAR(:year) " +
                    "GROUP BY YEAR(bo.workDate), MONTH(bo.workDate)", nativeQuery = false)
    List<Map<String, Object>> getDashboard(@Param("status") String status, @Param("year") LocalDateTime year);

    @Query(value =
            "SELECT COALESCE(SUM(j.price * bo.workHour), 0) " +
                    "FROM BookingOrder bo " +
                    "LEFT JOIN bo.orderJobs oj " +
                    "LEFT JOIN oj.job j " +
                    "WHERE bo.status = :status " +
                    "AND YEAR(bo.workDate) = YEAR(:year) " +
                    "GROUP BY YEAR(bo.workDate)", nativeQuery = false)
    Integer getTotalIncome(@Param("status") String status, @Param("year") LocalDateTime year);


}
