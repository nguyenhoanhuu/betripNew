package fit.iuh.dulichgiare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.UserRequestTravel;

@Repository
public interface UserRequestTravelRepository extends JpaRepository<UserRequestTravel, Long> {

    List<UserRequestTravel> findAllByCustomerId(long id);

}
