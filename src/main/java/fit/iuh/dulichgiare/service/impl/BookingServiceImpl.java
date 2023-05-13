package fit.iuh.dulichgiare.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.constant.Constants;
import fit.iuh.dulichgiare.dto.BookingCount;
import fit.iuh.dulichgiare.dto.BookingDTO;
import fit.iuh.dulichgiare.dto.BookingSave;
import fit.iuh.dulichgiare.dto.BookingSum;
import fit.iuh.dulichgiare.dto.PaymentDTO;
import fit.iuh.dulichgiare.dto.RevenueStatistics;
import fit.iuh.dulichgiare.entity.Booking;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.entity.Employee;
import fit.iuh.dulichgiare.entity.Payment;
import fit.iuh.dulichgiare.entity.Tour;
import fit.iuh.dulichgiare.entity.Voucher;
import fit.iuh.dulichgiare.repository.BookingRepository;
import fit.iuh.dulichgiare.repository.CustomerRepository;
import fit.iuh.dulichgiare.repository.EmployeeRepository;
import fit.iuh.dulichgiare.repository.PaymentRepository;
import fit.iuh.dulichgiare.repository.TourRepository;
import fit.iuh.dulichgiare.repository.VoucherRepository;
import fit.iuh.dulichgiare.service.BookingService;
import fit.iuh.dulichgiare.service.MailService;
import fit.iuh.dulichgiare.stripe.StripeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private TourRepository tourRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private VoucherRepository voucherRepo;
    @Autowired
    private MailService mailService;
    @Value("${stripe.public.key}")
    private String API_PUBLIC_KEY;
    @Autowired
    private StripeService stripeService;
    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public List<BookingDTO> getAllBookings(int voucherIdExist) throws InterruptedException, ExecutionException {
        if (voucherIdExist == 0) {
            return bookingRepo.findAll().stream().map(booking -> {
                Payment payment = paymentRepo.getPaymentByBookingId(booking.getId());
                BookingDTO dto = new BookingDTO();
                dto.setId(booking.getId());
                dto.setActive(booking.isActive());
                dto.setDepartureTime(booking.getDepartureTime());
                dto.setStartDayTour(booking.getStartDayTour());
                dto.setEndDayTour(booking.getEndDayTour());
                dto.setNameCustomer(booking.getCustomer().getName());
                dto.setTourId(booking.getTour().getId());
                dto.setNameTour(booking.getTour().getName());
                dto.setVoucherCode(booking.getVoucher() != null ? booking.getVoucher().getCode() : "");
                dto.setNote(booking.getNote());
                dto.setPriceTour(booking.getPriceTour());
                dto.setPriceVoucher(booking.getPriceVoucher());
                dto.setNumberOfAdbult(booking.getNumberofadbult());
                dto.setNumberOfChildren(booking.getNumberofchildren());
                dto.setInfoOfAdbult(booking.getInfoofadbult());
                dto.setInfoOfChildren(booking.getInfoofchildren());
                dto.setCreateAt(booking.getCreateat());
                dto.setTotal(booking.getTotal());
                dto.setStatus(booking.getStatus());
                PaymentDTO paymentDTO = new PaymentDTO();
                if (payment != null) {
                    paymentDTO.setType(payment.getType());
                    dto.setPayment(paymentDTO);
                } else {
                    paymentDTO.setType(null);
                    dto.setPayment(paymentDTO);
                }
                return dto;
            }).toList();
        } else if (voucherIdExist == 1) {
            return bookingRepo.findByVoucherIdIsNotNull().stream().map(booking -> {
                BookingDTO dto = new BookingDTO();
                Payment payment = paymentRepo.getPaymentByBookingId(booking.getId());
                dto.setId(booking.getId());
                dto.setNameCustomer(booking.getCustomer().getName());
                dto.setTourId(booking.getTour().getId());
                dto.setNameTour(booking.getTour().getName());
                dto.setVoucherCode(booking.getVoucher().getCode());
                dto.setNote(booking.getNote());
                dto.setPriceTour(booking.getPriceTour());
                dto.setPriceVoucher(booking.getPriceVoucher());
                dto.setNumberOfAdbult(booking.getNumberofadbult());
                dto.setNumberOfChildren(booking.getNumberofchildren());
                dto.setInfoOfAdbult(booking.getInfoofadbult());
                dto.setInfoOfChildren(booking.getInfoofchildren());
                dto.setCreateAt(booking.getCreateat());
                dto.setTotal(booking.getTotal());
                dto.setStatus(booking.getStatus());
                PaymentDTO paymentDTO = new PaymentDTO();
                if (payment != null) {
                    paymentDTO.setType(payment.getType());
                    dto.setPayment(paymentDTO);
                } else {
                    paymentDTO.setType(null);
                    dto.setPayment(paymentDTO);
                }
                return dto;
            }).toList();
        } else if (voucherIdExist == 2) {
            return bookingRepo.findByVoucherIdIsNull().stream().map(booking -> {
                BookingDTO dto = new BookingDTO();
                Payment payment = paymentRepo.getPaymentByBookingId(booking.getId());
                dto.setId(booking.getId());
                dto.setNameCustomer(booking.getCustomer().getName());
                dto.setTourId(booking.getTour().getId());
                dto.setNameTour(booking.getTour().getName());
                dto.setVoucherCode("");
                dto.setNote(booking.getNote());
                dto.setPriceTour(booking.getPriceTour());
                dto.setPriceVoucher(booking.getPriceVoucher());
                dto.setNumberOfAdbult(booking.getNumberofadbult());
                dto.setNumberOfChildren(booking.getNumberofchildren());
                dto.setInfoOfAdbult(booking.getInfoofadbult());
                dto.setInfoOfChildren(booking.getInfoofchildren());
                dto.setCreateAt(booking.getCreateat());
                dto.setTotal(booking.getTotal());
                dto.setStatus(booking.getStatus());
                PaymentDTO paymentDTO = new PaymentDTO();
                if (payment != null) {
                    paymentDTO.setType(payment.getType());
                    dto.setPayment(paymentDTO);
                } else {
                    paymentDTO.setType(null);
                    dto.setPayment(paymentDTO);
                }
                return dto;
            }).toList();
        }
        return null;

    }

    @Override
    public BookingSave saveBooking(BookingDTO bookingDTO, String userName)
            throws InterruptedException, ExecutionException {
        BookingSave bookingSave = new BookingSave();
        log.info("Starting save payment");
        Customer customer = customerRepo.findCustomerByPhone(userName);
        if (customer == null) {
            bookingSave.setResult(1);
            return bookingSave;
        }
        Tour tour = tourRepo.findById(bookingDTO.getTourId()).get();
        Voucher voucher = new Voucher();
        Booking booking = new Booking();
        // check voucher get from UI have !=null
        if (bookingDTO.getVoucherCode().trim() != "") {
            voucher = voucherRepo.findVoucherByActiveTrueAndCode(bookingDTO.getVoucherCode());
            if (voucher != null) {
                if (voucher.getLimit() > 0) {
                    booking.setVoucher(voucher);
                    booking.setPriceTour(voucher.getDiscount());
                    voucher.setLimit(voucher.getLimit() - 1);
                } else if (voucher.getLimit() <= 0) {
                    bookingSave.setResult(1);
                    return bookingSave;
                }
            } else {
                bookingSave.setResult(2);
                return bookingSave;
            }
        } else {
            booking.setVoucher(null);
            booking.setPriceVoucher(0);
        }
        booking.setStartDayTour(tour.getStartday());
        booking.setEndDayTour(tour.getEndday());
        booking.setDepartureTime(tour.getDepartureTime());
        booking.setCustomer(customer);
        booking.setPriceVoucher(bookingDTO.getPriceVoucher());
        booking.setTour(tour);
        booking.setNote(bookingDTO.getNote());
        booking.setNumberofadbult(bookingDTO.getNumberOfAdbult());
        booking.setNumberofchildren(bookingDTO.getNumberOfChildren());
        booking.setInfoofadbult(bookingDTO.getInfoOfAdbult());
        booking.setInfoofchildren(bookingDTO.getInfoOfChildren());
        booking.setCreateat(LocalDateTime.now());
        booking.setTotal(bookingDTO.getTotal());
        booking.setActive(true);
        booking.setStatus(Constants.STATUS_CHUA_CHON_HINH_THUC_THANH_TOAN);
        int totalSubcriber = tour.getSubcriber() + booking.getNumberofadbult() + booking.getNumberofchildren();
        if (totalSubcriber > tour.getNumberofpeople()) {
            bookingSave.setResult(4);
            return bookingSave;
        }
        Booking resultSaveBooking = bookingRepo.save(booking);
        bookingSave.setBookingId(resultSaveBooking.getId());
        if (resultSaveBooking != null) {
            tour.setSubcriber(totalSubcriber);
            tourRepo.save(tour);
        }
        if (bookingDTO.getVoucherCode().trim() != "") {
            voucherRepo.save(voucher);
        }
        bookingSave.setResult(0);

        return bookingSave;
    }

    @Override
    public int updateBooking(BookingDTO bookingDTO, String phoneEmployee)
            throws InterruptedException, ExecutionException {
        if (checkEmployeePermissionAccessBooking(phoneEmployee) == false) {
            return 0;
        }
        Booking booking = bookingRepo.findById(bookingDTO.getId()).get();
        if (Objects.nonNull(booking)) {
            Payment payment = paymentRepo.getPaymentByBookingId(booking.getId());
            if (booking.getStatus().equals(Constants.STATUS_CHO_THANH_TOAN)
                    && payment.getStatus().equals(Constants.STATUS_CHO_THANH_TOAN)) {
                booking.setStatus(bookingDTO.getStatus());
                bookingRepo.save(booking);
                payment.setStatus(booking.getStatus());
                CompletableFuture.runAsync(() -> paymentRepo.save(payment));
            } else {
                return 2;
            }
        }
        return 1;
    }

    @Override
    public String deleteBooking(long id) throws InterruptedException, ExecutionException {
        if (id > 0) {
            bookingRepo.deleteById(id);
            return "delete success id - " + id;
        }
        return "Delete failed";
    }

    @Override
    public BookingDTO getBookingById(long id, String userName) throws InterruptedException, ExecutionException {
        BookingDTO bookingDTO = new BookingDTO();
        Customer customer = customerRepo.findCustomerByPhone(userName);
        if (customer == null) {
            bookingDTO.setResult(0);
            return bookingDTO;
        }
        Booking booking = bookingRepo.findBookingById(id);
        if (booking == null) {
            bookingDTO.setResult(1);
            return bookingDTO;
        }
        if (booking.getCustomer().getId() != customer.getId()) {
            bookingDTO.setResult(2);
            return bookingDTO;
        } else {
            bookingDTO.setResult(3);
            bookingDTO.setId(booking.getId());
            bookingDTO.setActive(booking.isActive());
            bookingDTO.setTourId(booking.getTour().getId());
            bookingDTO.setStartDayTour(booking.getStartDayTour());
            bookingDTO.setEndDayTour(booking.getEndDayTour());
            bookingDTO.setDepartureTime(booking.getDepartureTime());
            bookingDTO.setPriceTour(booking.getPriceTour());
            bookingDTO.setPriceVoucher(booking.getPriceVoucher());
            bookingDTO.setNameCustomer(booking.getCustomer().getName());
            bookingDTO.setNameTour(booking.getTour().getName());
            bookingDTO.setVoucherCode(booking.getVoucher() != null ? booking.getVoucher().getCode() : "");
            bookingDTO.setNote(booking.getNote());
            bookingDTO.setNumberOfAdbult(booking.getNumberofadbult());
            bookingDTO.setNumberOfChildren(booking.getNumberofchildren());
            bookingDTO.setInfoOfAdbult(booking.getInfoofadbult());
            bookingDTO.setInfoOfChildren(booking.getInfoofchildren());
            bookingDTO.setNumberOfChildren(booking.getNumberofchildren());
            bookingDTO.setCreateAt(booking.getCreateat());
            bookingDTO.setTotal(booking.getTotal());
            bookingDTO.setStatus(booking.getStatus());
            return bookingDTO;
        }
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?") // Thực hiện vào lúc 1 giờ sáng mỗi ngày
    public void deleteExpiredBookings() {
        List<Booking> bookings = bookingRepo.findAll();
        for (Booking booking : bookings) {
            if (booking.getStatus().equals("Chờ thanh toán")) {
                LocalDate today = LocalDate.now();
                long daysBetween = ChronoUnit.DAYS.between(booking.getCreateat(), today);
                if (daysBetween >= 1) {
                    log.info("Proceed to delete the booking with id -" + booking.getId());
                    Booking deleteBooking = new Booking();
                    deleteBooking.setCustomer(null);
                    deleteBooking.setTour(null);
                    deleteBooking.setVoucher(null);
                    bookingRepo.delete(deleteBooking);
                    log.info("Delete tour success with id- " + booking.getId());
                }
            }
        }
    }

    private boolean checkEmployeePermissionAccessBooking(String phoneEmployee) {
        Employee employee = employeeRepo.findEmployeeByPhone(phoneEmployee);
        return Objects.nonNull(employee);
    }

    @Override
    public BookingCount countSuccessfulBookings() {
        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();
        double totalBookingsOfMonthSucceess = bookingRepo.findBookingTotalBillOfMonthSuccess(currentMonth);
        double countTotalBookingOfMonth = bookingRepo.findBookingTotalBillOfMonth(currentMonth);
        BookingCount bookingCount = new BookingCount();
        bookingCount.setTotalBokingsSuccess(totalBookingsOfMonthSucceess);
        NumberFormat formatter = new DecimalFormat("#0.00");
        bookingCount.setPercent(formatter.format((totalBookingsOfMonthSucceess / countTotalBookingOfMonth) * 100));
        return bookingCount;
    }

    @Override
    public BookingSum sumTotalBillSuccessfulBookings() {
        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();
        long totalBookingsOfMonthSucceess = bookingRepo.findTotalBillBookingTotalBillOfMonthSuccess(currentMonth);
        double countTotalBookingOfMonth = bookingRepo.findTotalBillBookingTotalBillOfMonth(currentMonth);
        BookingSum bookingSum = new BookingSum();
        bookingSum.setTotalSumBookings(Long.toString(totalBookingsOfMonthSucceess));
        NumberFormat formatter = new DecimalFormat("#0.00");
        bookingSum.setPercentSum(formatter.format((totalBookingsOfMonthSucceess / countTotalBookingOfMonth) * 100));
        return bookingSum;
    }

    @Override
    public List<BookingDTO> getAllBookingStatusWaitForPay(String userName) {
        List<BookingDTO> bookingDTOs = new ArrayList<>();
        BookingDTO bookingDTO = new BookingDTO();
        Customer customer = customerRepo.findCustomerByPhone(userName);
        if (customer == null) {
            bookingDTO.setResult(0);
            bookingDTOs.add(bookingDTO);
            return bookingDTOs;
        }
        List<Booking> bookings = bookingRepo.findBookingStatusWatiForPay(customer.getId());
        if (bookingDTOs == null || bookings.isEmpty()) {
            bookingDTO.setResult(2);
            bookingDTOs.add(bookingDTO);
            return bookingDTOs;
        } else {
            for (Booking booking : bookings) {
                BookingDTO bookingDTONews = new BookingDTO();
                bookingDTONews.setResult(1);
                bookingDTONews.setActive(booking.isActive());
                bookingDTONews.setId(booking.getId());
                bookingDTONews.setDepartureTime(booking.getDepartureTime());
                bookingDTONews.setStartDayTour(booking.getStartDayTour());
                bookingDTONews.setEndDayTour(booking.getEndDayTour());
                bookingDTONews.setNameCustomer(booking.getCustomer().getName());
                bookingDTONews.setTourId(booking.getTour().getId());
                bookingDTONews.setNameTour(booking.getTour().getName());
                bookingDTONews.setVoucherCode(booking.getVoucher() != null ? booking.getVoucher().getCode() : "");
                bookingDTONews.setNote(booking.getNote());
                bookingDTONews.setPriceTour(booking.getPriceTour());
                bookingDTONews.setPriceVoucher(booking.getPriceVoucher());
                bookingDTONews.setNumberOfAdbult(booking.getNumberofadbult());
                bookingDTONews.setNumberOfChildren(booking.getNumberofchildren());
                bookingDTONews.setInfoOfAdbult(booking.getInfoofadbult());
                bookingDTONews.setInfoOfChildren(booking.getInfoofchildren());
                bookingDTONews.setCreateAt(booking.getCreateat());
                bookingDTONews.setTotal(booking.getTotal());
                bookingDTONews.setStatus(booking.getStatus());
                bookingDTOs.add(bookingDTONews);
            }
            return bookingDTOs;
        }
    }

    @Override
    public List<BookingDTO> getAllBookingStatusSuccess(String userName) {
        List<BookingDTO> bookingDTOs = new ArrayList<>();
        BookingDTO bookingDTO = new BookingDTO();
        Customer customer = customerRepo.findCustomerByPhone(userName);
        if (customer == null) {
            bookingDTO.setResult(0);
            bookingDTOs.add(bookingDTO);
            return bookingDTOs;
        }
        List<Booking> bookings = bookingRepo.findBookingStatusSuccess(customer.getId());
        if (bookingDTOs == null || bookings.isEmpty()) {
            bookingDTO.setResult(2);
            bookingDTOs.add(bookingDTO);
            return bookingDTOs;
        } else {
            for (Booking booking : bookings) {
                BookingDTO bookingDTONews = new BookingDTO();
                bookingDTONews.setResult(1);
                bookingDTONews.setId(booking.getId());
                bookingDTONews.setActive(booking.isActive());
                bookingDTONews.setDepartureTime(booking.getDepartureTime());
                bookingDTONews.setStartDayTour(booking.getStartDayTour());
                bookingDTONews.setEndDayTour(booking.getEndDayTour());
                bookingDTONews.setNameCustomer(booking.getCustomer().getName());
                bookingDTONews.setTourId(booking.getTour().getId());
                bookingDTONews.setNameTour(booking.getTour().getName());
                bookingDTONews.setVoucherCode(booking.getVoucher() != null ? booking.getVoucher().getCode() : "");
                bookingDTONews.setNote(booking.getNote());
                bookingDTONews.setPriceTour(booking.getPriceTour());
                bookingDTONews.setPriceVoucher(booking.getPriceVoucher());
                bookingDTONews.setNumberOfAdbult(booking.getNumberofadbult());
                bookingDTONews.setNumberOfChildren(booking.getNumberofchildren());
                bookingDTONews.setInfoOfAdbult(booking.getInfoofadbult());
                bookingDTONews.setInfoOfChildren(booking.getInfoofchildren());
                bookingDTONews.setCreateAt(booking.getCreateat());
                bookingDTONews.setTotal(booking.getTotal());
                bookingDTONews.setStatus(booking.getStatus());
                bookingDTOs.add(bookingDTONews);
            }
            return bookingDTOs;
        }
    }

    public double calculateRevenue() {
        List<Booking> bookings = bookingRepo.findAll();
        double revenue = 0.0;
        for (Booking booking : bookings) {
            revenue += booking.getPriceTour();
        }
        return revenue;
    }

    public double calculateProfit() {
        List<Booking> bookings = bookingRepo.findAll();
        double profit = 0.0;
        for (Booking booking : bookings) {
            profit += booking.getPriceTour() - booking.getPriceVoucher();
        }
        return profit;
    }

    public Map<String, Double> calculateMonthlyRevenue() {
        List<Booking> bookings = bookingRepo.findAll();
        Map<String, Double> monthlyRevenueMap = new HashMap<>();
        for (Booking booking : bookings) {
            String month = booking.getStartDayTour().getMonth().toString();
            double revenue = booking.getPriceTour();
            monthlyRevenueMap.put(month, monthlyRevenueMap.getOrDefault(month, 0.0) + revenue);
        }
        return monthlyRevenueMap;
    }

    public Map<DayOfWeek, Double> calculateRevenueByDayOfWeek(LocalDateTime startDate, LocalDateTime endDate) {
        Map<DayOfWeek, Double> revenueByDayOfWeek = new HashMap<>();

        // Khởi tạo map với tất cả các ngày trong tuần và doanh thu ban đầu là 0
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            revenueByDayOfWeek.put(dayOfWeek, 0.0);
        }

        // Lấy danh sách các booking trong khoảng thời gian đã cho
        List<Booking> bookings = bookingRepo.findByCreateatBetweenAndStatus(startDate, endDate, "Thành công");

        // Tính doanh thu cho từng booking và cộng dồn vào map theo ngày trong tuần
        for (Booking booking : bookings) {
            double bookingRevenue = booking.getTotal();
            DayOfWeek dayOfWeek = booking.getCreateat().getDayOfWeek();

            double currentRevenue = revenueByDayOfWeek.get(dayOfWeek);
            revenueByDayOfWeek.put(dayOfWeek, currentRevenue + bookingRevenue);
        }

        return revenueByDayOfWeek;
    }

    public String calculateWeeklyTotal() {
        LocalDate currentDate = LocalDate.now();
        // Tìm ngày bắt đầu và ngày kết thúc của tuần (thứ 2 đến Chủ nhật)
        LocalDate startDate = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDate endDate = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDateTime endDateTime = endDate.atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59);

        // Truy vấn danh sách các booking trong khoảng thời gian
        List<Booking> bookings = bookingRepo.findByCreateatBetweenAndStatus(startDateTime, endDateTime, "Thành công");

        // Tính tổng hóa đơn trong tuần
        long total = 0;
        for (Booking booking : bookings) {
            total += booking.getTotal();
        }

        return Long.toString(total);
    }

    @Override
    public List<BookingDTO> getTop10BookingRecently() {

        List<BookingDTO> bookingDTOs = new ArrayList<>();
        List<Booking> bookings = bookingRepo.findBookingByActiveTrue(Sort.by("id").descending());
        int i = 0;
        for (Booking booking : bookings) {
            if (i <= 10) {
                BookingDTO dto = new BookingDTO();
                dto.setId(booking.getId());
                dto.setNameTour(booking.getTour().getName());
                dto.setNameCustomer(booking.getCustomer().getName());
                dto.setNumberOfAdbult(booking.getNumberofadbult() + booking.getNumberofchildren());
                dto.setCreateAt(booking.getCreateat());
                dto.setTotal(booking.getTotal());
                dto.setStatus(booking.getStatus());
                bookingDTOs.add(dto);
            }
            i++;
        }
        return bookingDTOs;
    }

    @Override
    public List<RevenueStatistics> calculateRevenueByMonth() {
        int now = LocalDate.now().getYear();
        List<Object[]> result = bookingRepo.calculateRevenueByMonth(now);
        List<RevenueStatistics> revenueStatisticsList = new ArrayList<>();
        for (Object[] row : result) {
            int month = (int) row[0];
            double d = (Double) row[1];
            double d2 = (Double) row[2];
            long totalBill = (long) d;
            long profit = (long) d2;
            RevenueStatistics revenueStatistics = new RevenueStatistics();
            revenueStatistics.setMonth(month);
            revenueStatistics.setTotalBill(totalBill);
            revenueStatistics.setProfit(profit);
            revenueStatisticsList.add(revenueStatistics);
        }
        return revenueStatisticsList;
    }

}
