package fit.iuh.dulichgiare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.TourGuide;

@Repository
public interface TourGuideRepository extends JpaRepository<TourGuide, Long> {

    TourGuide findTourGuideByName(String nameTourGuide);

    TourGuide findTourGuideByPhone(String phone);

}
