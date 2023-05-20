package fit.iuh.dulichgiare.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.BookingCount;
import fit.iuh.dulichgiare.dto.BookingDTO;
import fit.iuh.dulichgiare.dto.BookingSave;
import fit.iuh.dulichgiare.dto.BookingSum;
import fit.iuh.dulichgiare.dto.RevenueStatistics;

/**
 * 
 * @author HOAN HUU
 *
 */
@Service
public interface BookingService {

	public List<BookingDTO> getAllBookings(int voucherIdExist) throws InterruptedException, ExecutionException;

	public List<BookingDTO> getTop10BookingRecently();

	public BookingSave saveBooking(BookingDTO bookingDTO, String phoneCustomer)
			throws InterruptedException, ExecutionException;

	public int updateBooking(BookingDTO bookingDTO, String phoneEmployee)
			throws InterruptedException, ExecutionException;

	public String deleteBooking(long id) throws InterruptedException, ExecutionException;

	public BookingDTO getBookingById(long id, String userName) throws InterruptedException, ExecutionException;

	public List<BookingDTO> getAllBookingStatusWaitForPay(String userName);

	public List<BookingDTO> getAllBookingStatusSuccess(String userName);

//automation
	public void deleteExpiredBookings();

	public void sendMailOneDayBeforeTravelerNotification();

//
	public BookingCount countSuccessfulBookings();

	public BookingSum sumTotalBillSuccessfulBookings();

	public Map<DayOfWeek, Double> calculateRevenueByDayOfWeek(LocalDateTime startDate, LocalDateTime endDate);

	public String calculateWeeklyTotal();

	public List<RevenueStatistics> calculateRevenueByMonth();

}
