package container.code.data.repository;

import container.code.data.entity.OrderJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderJobRepository extends JpaRepository<OrderJob, Integer> {
}
