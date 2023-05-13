package fit.iuh.dulichgiare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.TourDetail;

@Repository
public interface TourDetailRepository extends JpaRepository<TourDetail, Long> {

    TourDetail findTourDetailByTourId(long id);

}
