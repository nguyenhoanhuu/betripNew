package fit.iuh.dulichgiare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment getPaymentByBookingId(long bookingId);

}
