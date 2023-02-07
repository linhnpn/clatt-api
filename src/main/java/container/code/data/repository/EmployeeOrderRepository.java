package container.code.data.repository;

import container.code.data.entity.EmployeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeOrderRepository extends JpaRepository<EmployeeOrder, Integer> {
}
