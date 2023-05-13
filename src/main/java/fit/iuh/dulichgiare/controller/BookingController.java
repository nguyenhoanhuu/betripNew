package fit.iuh.dulichgiare.controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.BookingCount;
import fit.iuh.dulichgiare.dto.BookingDTO;
import fit.iuh.dulichgiare.dto.BookingSave;
import fit.iuh.dulichgiare.dto.BookingSum;
import fit.iuh.dulichgiare.dto.RevenueStatistics;
import fit.iuh.dulichgiare.respone.MessageResponse;
import fit.iuh.dulichgiare.service.BookingService;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/findAll")
    public List<BookingDTO> getAllBookings(@RequestParam int voucherIdExists)
            throws InterruptedException, ExecutionException {
        return bookingService.getAllBookings(voucherIdExists);
    }

    @PostMapping(value = { "/save" })
    public ResponseEntity<MessageResponse> saveBooking(@RequestBody BookingDTO bookingDTO,
            @AuthenticationPrincipal UserDetails user) throws InterruptedException, ExecutionException {
        MessageResponse messageResponse = new MessageResponse();
        if (user == null) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Vui lòng đăng nhập để đặt tour");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        BookingSave result = bookingService.saveBooking(bookingDTO, user.getUsername());
        if (result.getResult() == 0) {
            messageResponse.setStatus(true);
            messageResponse.setMessage("Đặt tour thành công");
            messageResponse.setBookingId(result.getBookingId());
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
        if (result.getResult() == 1) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Voucher đã hết lượt sử dụng");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else if (result.getResult() == 2) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Mã voucher không hợp lệ");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }

        else if (result.getResult() == 4) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Xin lỗi, số lượng người vượt quá số lượng chỗ còn trống của tour này");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = { "/update" })
    public ResponseEntity<MessageResponse> updateBooking(@RequestBody BookingDTO bookingDTO,
            @AuthenticationPrincipal UserDetails user) throws InterruptedException, ExecutionException {
        MessageResponse messageResponse = new MessageResponse();
        int result = bookingService.updateBooking(bookingDTO, user.getUsername());
        if (result == 0) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Người dùng không có quyền truy cập");
            return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
        } else if (result == 1) {
            messageResponse.setStatus(true);
            messageResponse.setMessage("Cập nhật trạng thái thành công");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else if (result == 2) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Không thể cập nhật khi trạng thái 'Thành công'");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/id")
    public ResponseEntity<MessageResponse> getBookingById(@RequestParam long bookingId,
            @AuthenticationPrincipal UserDetails user) throws InterruptedException, ExecutionException {
        MessageResponse messageResponse = new MessageResponse();
        if (user == null) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Vui lòng đăng nhập để xem hoá đơn tour");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        BookingDTO result = bookingService.getBookingById(bookingId, user.getUsername());
        if (result.getResult() == 0) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Người dùng không có quyền truy cập");
            return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
        } else if (result.getResult() == 1) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Hoá đơn đặt tour không tồn tại");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else if (result.getResult() == 2) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Bạn không có quyền xem thông tin hoá đơn này!");
            return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
        } else if (result.getResult() == 3) {
            messageResponse.setStatus(true);
            messageResponse.setMessage("Thông tin hoá đơn");
            BookingDTO bookingResult = new BookingDTO();
            bookingResult.setId(result.getId());
            bookingResult.setActive(result.isActive());
            bookingResult.setTourId(result.getTourId());
            bookingResult.setStartDayTour(result.getStartDayTour());
            bookingResult.setEndDayTour(result.getEndDayTour());
            bookingResult.setDepartureTime(result.getDepartureTime());
            bookingResult.setPriceTour(result.getPriceTour());
            bookingResult.setPriceVoucher(result.getPriceVoucher());
            bookingResult.setNameCustomer(result.getNameCustomer());
            bookingResult.setNameTour(result.getNameTour());
            bookingResult.setVoucherCode(result.getVoucherCode());
            bookingResult.setNote(result.getNote());
            bookingResult.setNumberOfAdbult(result.getNumberOfAdbult());
            bookingResult.setNumberOfChildren(result.getNumberOfChildren());
            bookingResult.setInfoOfAdbult(result.getInfoOfAdbult());
            bookingResult.setInfoOfChildren(result.getInfoOfChildren());
            bookingResult.setNumberOfChildren(result.getNumberOfChildren());
            bookingResult.setCreateAt(result.getCreateAt());
            bookingResult.setTotal(result.getTotal());
            bookingResult.setStatus(result.getStatus());
            messageResponse.setBooking(bookingResult);
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }

        return null;
    }

    @GetMapping("/countBooking")
    public BookingCount countSuccessfulBookings() {
        return bookingService.countSuccessfulBookings();
//      
    }

    @GetMapping("/sumBooking")
    public BookingSum sumTotalBillSuccessfulBookings() {
        return bookingService.sumTotalBillSuccessfulBookings();
//      
    }

    @GetMapping("/billWaitForPayment")
    public ResponseEntity<MessageResponse> getAllBookingStatusWaitForPay(@AuthenticationPrincipal UserDetails user) {
        MessageResponse messageResponse = new MessageResponse();
        if (user == null) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Vui lòng đăng nhập để xem hoá đơn tour");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        List<BookingDTO> bookingDTOs = bookingService.getAllBookingStatusWaitForPay(user.getUsername());
        List<BookingDTO> bookingDTONews = new ArrayList<>();
        for (BookingDTO bookingDTO : bookingDTOs) {
            if (bookingDTO.getResult() == 1) {
                messageResponse.setStatus(true);
                messageResponse.setMessage("Danh sách hoá đơn chờ thanh toán");
                BookingDTO bookingResult = new BookingDTO();
                bookingResult.setId(bookingDTO.getId());
                bookingResult.setActive(bookingDTO.isActive());
                bookingResult.setTourId(bookingDTO.getTourId());
                bookingResult.setStartDayTour(bookingDTO.getStartDayTour());
                bookingResult.setEndDayTour(bookingDTO.getEndDayTour());
                bookingResult.setDepartureTime(bookingDTO.getDepartureTime());
                bookingResult.setPriceTour(bookingDTO.getPriceTour());
                bookingResult.setPriceVoucher(bookingDTO.getPriceVoucher());
                bookingResult.setNameCustomer(bookingDTO.getNameCustomer());
                bookingResult.setNameTour(bookingDTO.getNameTour());
                bookingResult.setVoucherCode(bookingDTO.getVoucherCode());
                bookingResult.setNote(bookingDTO.getNote());
                bookingResult.setNumberOfAdbult(bookingDTO.getNumberOfAdbult());
                bookingResult.setNumberOfChildren(bookingDTO.getNumberOfChildren());
                bookingResult.setInfoOfAdbult(bookingDTO.getInfoOfAdbult());
                bookingResult.setInfoOfChildren(bookingDTO.getInfoOfChildren());
                bookingResult.setNumberOfChildren(bookingDTO.getNumberOfChildren());
                bookingResult.setCreateAt(bookingDTO.getCreateAt());
                bookingResult.setTotal(bookingDTO.getTotal());
                bookingResult.setStatus(bookingDTO.getStatus());
                bookingDTONews.add(bookingResult);
            } else if (bookingDTO.getResult() == 2) {
                messageResponse.setStatus(true);
                messageResponse.setMessage("Danh sách không có hoá đơn chờ thanh toán");
                messageResponse.setBookings(bookingDTONews);
                return new ResponseEntity<>(messageResponse, HttpStatus.OK);
            }
        }
        messageResponse.setBookings(bookingDTONews);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/billPaymentSuccess")
    public ResponseEntity<MessageResponse> getAllBookingStatusSuccess(@AuthenticationPrincipal UserDetails user) {
        MessageResponse messageResponse = new MessageResponse();
        if (user == null) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Vui lòng đăng nhập để xem hoá đơn tour");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        List<BookingDTO> bookingDTOs = bookingService.getAllBookingStatusSuccess(user.getUsername());
        List<BookingDTO> bookingDTONews = new ArrayList<>();
        for (BookingDTO bookingDTO : bookingDTOs) {
            if (bookingDTO.getResult() == 1) {
                messageResponse.setStatus(true);
                messageResponse.setMessage("Danh sách hoá đơn chờ thanh toán");
                BookingDTO bookingResult = new BookingDTO();
                bookingResult.setId(bookingDTO.getId());
                bookingResult.setActive(bookingDTO.isActive());
                bookingResult.setTourId(bookingDTO.getTourId());
                bookingResult.setStartDayTour(bookingDTO.getStartDayTour());
                bookingResult.setEndDayTour(bookingDTO.getEndDayTour());
                bookingResult.setDepartureTime(bookingDTO.getDepartureTime());
                bookingResult.setPriceTour(bookingDTO.getPriceTour());
                bookingResult.setPriceVoucher(bookingDTO.getPriceVoucher());
                bookingResult.setNameCustomer(bookingDTO.getNameCustomer());
                bookingResult.setNameTour(bookingDTO.getNameTour());
                bookingResult.setVoucherCode(bookingDTO.getVoucherCode());
                bookingResult.setNote(bookingDTO.getNote());
                bookingResult.setNumberOfAdbult(bookingDTO.getNumberOfAdbult());
                bookingResult.setNumberOfChildren(bookingDTO.getNumberOfChildren());
                bookingResult.setInfoOfAdbult(bookingDTO.getInfoOfAdbult());
                bookingResult.setInfoOfChildren(bookingDTO.getInfoOfChildren());
                bookingResult.setNumberOfChildren(bookingDTO.getNumberOfChildren());
                bookingResult.setCreateAt(bookingDTO.getCreateAt());
                bookingResult.setTotal(bookingDTO.getTotal());
                bookingResult.setStatus(bookingDTO.getStatus());
                bookingDTONews.add(bookingResult);
            } else if (bookingDTO.getResult() == 2) {
                messageResponse.setStatus(true);
                messageResponse.setMessage("Danh sách không có hoá đơn chờ thanh toán");
                messageResponse.setBookings(bookingDTONews);
                return new ResponseEntity<>(messageResponse, HttpStatus.OK);
            }
        }
        messageResponse.setBookings(bookingDTONews);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/weekly")
    public ResponseEntity<Map<DayOfWeek, Double>> getWeeklyRevenue() {
        // Lấy ngày bắt đầu và ngày kết thúc của tuần hiện tại
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startDate = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endDate = startDate.plusDays(6);

        // Thực hiện thống kê doanh thu một tuần
        Map<DayOfWeek, Double> revenueByDayOfWeek = bookingService.calculateRevenueByDayOfWeek(startDate, endDate);

        return ResponseEntity.ok(revenueByDayOfWeek);
    }

    @GetMapping("/weeklyTotalBill")
    public ResponseEntity<String> getWeeklyTotal() {
        String weeklyTotal = bookingService.calculateWeeklyTotal();
        return new ResponseEntity<>(weeklyTotal, HttpStatus.OK);
    }

    @GetMapping("/top10BookingRecently")
    public List<BookingDTO> getTop10BookingRecently() {
        return bookingService.getTop10BookingRecently();
    }

    @GetMapping("/monthlyRevenue")
    public List<RevenueStatistics> getMonthlyRevenue() {
        return bookingService.calculateRevenueByMonth();
    }
}
