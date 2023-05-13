package fit.iuh.dulichgiare.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.dto.PromotionDTO;
import fit.iuh.dulichgiare.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
//	@Query(value ="SELECT * FROM dulichgiare.promotion where name like '%?1%';", nativeQuery = true)
//	List<Promotion> findAllByName(String name);

    List<Promotion> findByNameContaining(@Param("infix") String name);

    Promotion findPromotionByName(String promotionName);

    List<Promotion> findByEndday(LocalDate now);

    List<Promotion> findPromotionByActiveTrue();

    List<Promotion> findPromotionByActiveTrue(Sort id);

}
