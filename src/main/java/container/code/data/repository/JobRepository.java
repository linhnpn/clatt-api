package container.code.data.repository;

import container.code.data.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
