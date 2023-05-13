package fit.iuh.dulichgiare.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByVoucherIdIsNotNull();

    List<Booking> findByVoucherIdIsNull();

    List<Booking> findBookingByCustomerId(long id);

    List<Booking> findByStatus(String status);

    List<Booking> findByCreateatBetweenAndStatus(LocalDate last30Days, LocalDate now, String status);

    List<Booking> findByCreateatBetweenAndStatus(LocalDateTime last30Days, LocalDateTime now, String status);

    Booking findBookingByTourId(long tourId);

    long countByStatus(String status);

    @Query(value = "select count(*) as total from booking b where status='Thành công' and month(b.createat) = ?1", nativeQuery = true)
    double findBookingTotalBillOfMonthSuccess(int month);

    @Query(value = "select count(*) as totalBooking from booking b where month(b.createat) = ?1", nativeQuery = true)
    double findBookingTotalBillOfMonth(int month);

    @Query(value = "select sum(b.total) as totalBill   from booking b where status='Thành công' and month(b.createat) = ?1", nativeQuery = true)
    long findTotalBillBookingTotalBillOfMonthSuccess(int currentMonth);

    @Query(value = "select sum(b.total) as totalBill   from booking b where month(b.createat) = ?1", nativeQuery = true)
    double findTotalBillBookingTotalBillOfMonth(int currentMonth);

    Booking findBookingById(long id);

    @Query(value = "SELECT * FROM dulichgiare1.booking where status = 'Chờ thanh toán' or status='Chưa chọn hình thức thanh toán' having customer_id=?1", nativeQuery = true)
    List<Booking> findBookingStatusWatiForPay(long id);

    @Query(value = "SELECT * FROM dulichgiare1.booking where status = 'Thành công'and customer_id=?1", nativeQuery = true)
    List<Booking> findBookingStatusSuccess(long id);

    List<Booking> findBookingByActiveTrue(Sort by);

    @Query("SELECT MONTH(b.createat), SUM(b.total), SUM(b.total - b.priceTour- b.priceVoucher) " +
            "FROM Booking b " +
            "WHERE YEAR(b.createat) = :year " +
            "GROUP BY MONTH(b.createat)")
     List<Object[]> calculateRevenueByMonth(int year);

}
