package container.code.data.repository;

import container.code.data.entity.HistoryAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface HistoryAmountRepository extends JpaRepository<HistoryAmount, Integer> {
}
