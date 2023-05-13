package fit.iuh.dulichgiare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByAccountId(long id);

    Customer findCustomerByPhone(String phoneCustomer);

	Customer findCustomerByName(String customerName);
}
