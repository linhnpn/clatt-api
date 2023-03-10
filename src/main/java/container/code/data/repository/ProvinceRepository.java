package container.code.data.repository;

import container.code.data.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    Optional<Province> findById(Integer Id);
}
