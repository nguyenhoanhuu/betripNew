package fit.iuh.dulichgiare.repository;

import fit.iuh.dulichgiare.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByAccountId(long id);

    Employee findEmployeeByPhone(String username);

}
