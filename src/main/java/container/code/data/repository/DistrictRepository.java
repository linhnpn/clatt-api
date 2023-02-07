package container.code.data.repository;

import container.code.data.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DistrictRepository extends JpaRepository<District, Integer> {
}
