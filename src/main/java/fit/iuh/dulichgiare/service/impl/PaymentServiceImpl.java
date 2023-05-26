package fit.iuh.dulichgiare.service.impl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.constant.Constants;
import fit.iuh.dulichgiare.dto.PaymentDTO;
import fit.iuh.dulichgiare.entity.Booking;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.entity.Payment;
import fit.iuh.dulichgiare.entity.Voucher;
import fit.iuh.dulichgiare.repository.BookingRepository;
import fit.iuh.dulichgiare.repository.CustomerRepository;
import fit.iuh.dulichgiare.repository.PaymentRepository;
import fit.iuh.dulichgiare.repository.VoucherRepository;
import fit.iuh.dulichgiare.service.MailService;
import fit.iuh.dulichgiare.service.PaymentService;
import fit.iuh.dulichgiare.stripe.StripeService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private BookingRepository bookingRepo;
	@Autowired
	private VoucherRepository voucherRepo;
	@Value("${stripe.public.key}")
	private String API_PUBLIC_KEY;
	@Autowired
	private StripeService stripeService;
	@Autowired
	private MailService mailService;

	/**
	 * 
	 * @param userId
	 * @param tourId
	 * @return '1' booking is exist, '0' booking is not exist
	 */
	@Override
	public int checkUserBookingIsExisted(String userId, long tourId) {
		Customer customer = customerRepo.findCustomerByPhone(userId);
		Booking booking = bookingRepo.findBookingByTourId(tourId);
		if (booking != null && booking.getCustomer().getId() == customer.getId()) {
			return 1;
		}
		return 0;
	}

	// return 0: Voucher hết lượt sử dụng
	// return 1: Voucher nhập sai
	//

	@Override
	public int savePayment(PaymentDTO paymentDTO, String userId, String tokenStripe)
			throws InterruptedException, ExecutionException {
		Payment payment = new Payment();
		Payment paymentCheck = paymentRepo.getPaymentByBookingId(paymentDTO.getBookingId());
		if (paymentCheck != null && paymentCheck.getBooking().getId() == paymentDTO.getBookingId()) {
			return 6;
		}
		Customer customer = customerRepo.findCustomerByPhone(userId);
		if (customer == null) {
			return 1;
		}
		Booking booking = bookingRepo.findById(paymentDTO.getBookingId()).get();
		if (booking == null) {
			return 2;
		}

		if (booking.getCustomer().getId() != customer.getId()) {
			return 5;
		}

		log.info("Starting save payment");
		// payment
		if (paymentDTO.getType().equals(Constants.THANH_TOAN_TIEN_MAT)) {
			payment.setAccountInfo(null);
			payment.setStatus(Constants.STATUS_CHO_THANH_TOAN);
			payment.setType(Constants.THANH_TOAN_TIEN_MAT);
			payment.setBooking(booking);
			booking.setStatus(Constants.STATUS_CHO_THANH_TOAN);
			CompletableFuture.runAsync(() -> {
				try {
					Booking bookingSave = bookingRepo.save(booking);
					String message = MessageFormat.format(Constants.URL_SHOW_BOOKING_DETAIL, booking.getId());
					mailService.sendEmailWhenBookingIsSuccess(Constants.MAIL_SENDER, customer.getEmail(),
							Constants.NOTIFICATION_CONFIRM_PAYMENT_METHOD, customer.getName(), booking, message,
							Constants.BOOKING_CASH_PAYMENT_TEMPLATE);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			});

		} else if (paymentDTO.getType().equals(Constants.THANH_TOAN_CHUYEN_KHOAN)) {
			payment.setAccountInfo(null);
			payment.setStatus(Constants.STATUS_CHO_THANH_TOAN);
			payment.setType(Constants.THANH_TOAN_CHUYEN_KHOAN);
			payment.setBooking(booking);
			booking.setStatus(Constants.STATUS_CHO_THANH_TOAN);
			CompletableFuture.runAsync(() -> {
				try {
					Booking bookingSave = bookingRepo.save(booking);
					String message = MessageFormat.format(Constants.URL_SHOW_BOOKING_DETAIL, booking.getId());
					mailService.sendEmailWhenBookingIsSuccess(Constants.MAIL_SENDER, customer.getEmail(),
							Constants.NOTIFICATION_CONFIRM_PAYMENT_METHOD, customer.getName(), booking, message,
							Constants.BOOKING_TRANSFER_PAYMENT_TEMPLATE);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			});

		} else if (paymentDTO.getType().equals(Constants.THANH_TOAN_NGAN_HANG_STRIPE)) {
			if (tokenStripe != null) {
				payment.setAccountInfo(paymentDTO.getAccountInfo());

				String chargeId = stripeService.createCharge(customer.getEmail(), tokenStripe, booking.getTotal());
				payment.setStatus(Constants.STATUS_THANH_CONG);
				booking.setStatus(Constants.STATUS_THANH_CONG);
				payment.setType(Constants.THANH_TOAN_NGAN_HANG_STRIPE);
				payment.setPaymentDate(LocalDateTime.now());
				payment.setBooking(booking);
				paymentRepo.save(payment);
				CompletableFuture.runAsync(() -> {
					try {
						Booking bookingSave = bookingRepo.save(booking);
						String message = MessageFormat.format(Constants.URL_SHOW_BOOKING_DETAIL, booking.getId());
						mailService.sendEmailWhenBookingIsSuccess(Constants.MAIL_SENDER, customer.getEmail(),
								Constants.NOTIFICATION_BOOK_TOUR_SUCCESS, customer.getName(), bookingSave, message,
								Constants.BOOKING_STRIPE_PAYMENT_TEMPLATE);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				});
				return 4;
			} else {
				return 3;
			}
		}
		payment.setPaymentDate(LocalDateTime.now());
		paymentRepo.save(payment);
		return 0;
	}

  @Scheduled(cron = "0 0 0 * * ?") // Thực hiện mỗi ngày lúc 0h00
//  @Scheduled(cron = "0 */1 * ? * *")
	@Override
	public void automationDeletePaymentStatusOverThreeDay() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime lastThreeDays = now.minusDays(3);
		List<Payment> payments = paymentRepo.findAll();
		for (Payment payment : payments) {
			if (payment.getStatus().equals("Chờ thanh toán") && payment.getPaymentDate().isAfter(lastThreeDays)) {
				paymentRepo.delete(payment);
				bookingRepo.deleteById(payment.getBooking().getId());
				Voucher voucher = voucherRepo.findVoucherByIdAndActiveTrue(payment.getBooking().getVoucher().getId());
				if (voucher != null) {
					voucher.setLimit(voucher.getLimit() + 1);
				}
			}
		}
	}

	@Override
	public int updatePayment(PaymentDTO paymentDTO, String userId, String tokenStripe)
			throws InterruptedException, ExecutionException {
		Payment paymentCheck = paymentRepo.getPaymentByBookingId(paymentDTO.getBookingId());
		Customer customer = customerRepo.findCustomerByPhone(userId);
		Booking booking = bookingRepo.findById(paymentDTO.getBookingId()).get();

		if (customer == null) {
			return 0;
			// vui lòng đăng nhập
		}
		if (customer.getId() != booking.getCustomer().getId()) {
			return 1;
			// bạn không có quyền truy cập
		}
//        if(paymentCheck != null &&  )
//        if (paymentCheck != null && paymentCheck.getBooking().getId() == paymentDTO.getBookingId()) {
//            
//        }

		return 0;
	}
}
