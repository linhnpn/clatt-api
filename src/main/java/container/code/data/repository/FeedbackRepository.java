package container.code.data.repository;

import container.code.data.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
