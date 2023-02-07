package container.code.data.repository;

import container.code.data.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
