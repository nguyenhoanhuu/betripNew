package fit.iuh.dulichgiare.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query(value = "SELECT * FROM tour t where active = true ORDER BY t.liked DESC Limit ?1", nativeQuery = true)
    List<Tour> findByTopLiked(int number);

    List<Tour> findByNameContaining(@Param("infix") String name);

    // unfinished
    @Query(value = "select ", nativeQuery = true)
    List<Tour> findByDepartureOrDestinationOrNumberofdayOrNumberofpeopleOrpriceOrtype(String departure,
            String destination, int numberofday, int numberofpeople, double price, String type);

    List<Tour> findByActiveAndDepartureOrDestinationOrPriceBetweenAndType(boolean active, String departure,
            String destination, double startPrice, double endPrice, String type, Pageable pageable);

    Tour findTourByName(String nameTour);

    List<Tour> findAllByActive(Pageable pageable, boolean active);

    Tour findByIdAndActive(long id, boolean active);

    List<Tour> findAllByActiveAndType(boolean active, String type, Pageable pageable);

    List<Tour> findByEndday(LocalDate currentDate);

    List<Tour> findByActiveTrueAndStartdayBetweenAndPromotionIsNotNull(LocalDate today, LocalDate end);

    List<Tour> findTop3ByActiveIsTrueAndStartdayBetweenOrderByStartdayAsc(LocalDate today, LocalDate soonToExpire);

    List<Tour> findTop8ByOrderByLikedDesc();

    List<Tour> findByActiveAndStartdayAfterAndType(boolean b, LocalDate now, String type);

    @Query(value = "SELECT t.* FROM tour t WHERE t.active = true GROUP BY t.destination ORDER BY SUM(t.liked) DESC", nativeQuery = true)
    List<Tour> findDistinctByActiveTrueOrderByLikedDesc();

    Tour findTourByActiveTrueAndId(long id);
}
