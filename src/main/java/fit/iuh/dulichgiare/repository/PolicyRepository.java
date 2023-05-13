package fit.iuh.dulichgiare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    List<Policy> findByPolicyNameContaining(String name);

    Policy findPolicyByPolicyName(String policyName);

}
