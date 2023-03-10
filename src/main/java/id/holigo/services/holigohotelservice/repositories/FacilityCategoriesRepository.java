package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.FacilityCategories;

public interface FacilityCategoriesRepository extends JpaRepository<FacilityCategories, Long>{
    Optional<FacilityCategories> findByName(String name);
}
