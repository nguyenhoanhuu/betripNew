package fit.iuh.dulichgiare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.TourGuideTour;

@Repository
public interface TourGuideTourRepository extends JpaRepository<TourGuideTour, Long> {

    List<TourGuideTour> findByTourId(long id);

}
