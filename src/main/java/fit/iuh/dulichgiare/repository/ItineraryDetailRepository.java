package fit.iuh.dulichgiare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.ItineraryDetail;

@Repository
public interface ItineraryDetailRepository extends JpaRepository<ItineraryDetail, Long> {

    List<ItineraryDetail> findItineraryDetailByItineraryId(long id);

    @Query(value = "select * from itinerarydetail itid where itid.itinerary_id = (select distinct  i.id from itinerary i left join itinerarydetail itd on i.id= itd.itinerary_id where i.tour_id = ?1)", nativeQuery = true)
    List<ItineraryDetail> findAllItineraryDetailByTourId(long id);
}
