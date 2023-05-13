package fit.iuh.dulichgiare.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.PaymentDTO;
import fit.iuh.dulichgiare.respone.MessageResponse;
import fit.iuh.dulichgiare.service.PaymentService;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymenttController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/checkbooking")
    public ResponseEntity<MessageResponse> checkUserBookingIsExisted(@AuthenticationPrincipal UserDetails user,
            @RequestParam long tourId) {
        int response = paymentService.checkUserBookingIsExisted(user.getUsername(), tourId);
        MessageResponse messageResponse = new MessageResponse();
        if (response == 1) {
            messageResponse.setStatus(true);
            messageResponse.setMessage("Bạn đã đặt chuyến đi này!");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Bạn chưa đặt chuyến đi này!");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }

    @PostMapping(value = { "/save" })
    public ResponseEntity<MessageResponse> savePayment(@RequestBody PaymentDTO paymentDTO,
            @AuthenticationPrincipal UserDetails user, @RequestHeader String tokenStripe)
            throws InterruptedException, ExecutionException {
        MessageResponse messageResponse = new MessageResponse();
        if (user == null) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Vui lòng đăng nhập để thanh toán tour");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        int result = paymentService.savePayment(paymentDTO, user.getUsername(), tokenStripe);
        if (result == 0) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Đặt vé thành công, vui lòng thanh toán trước 24h để được giữ vé du lịch");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
        if (result == 1) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Vui lòng đăng nhập tài khoản khách hàng");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else if (result == 2) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Đơn đặt vé du lịch không hợp lệ");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else if (result == 3) {
            messageResponse.setStatus(true);
            messageResponse.setMessage("Lỗi tài khoản ngân hàng, vui lòng kiểm tra lại");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else if (result == 4) {
            messageResponse.setStatus(true);
            messageResponse.setMessage("Thanh toán thành công");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else if (result == 5) {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Tài khoản của bạn không được phép thanh toán đơn đặt tour này");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);

        } else if (result == 6) {
            messageResponse.setStatus(false);
            messageResponse.setMessage(
                    "Đơn đặt tour đã chọn hình thức thanh toán, vui lòng vào đơn đặt vé của bạn để kiểm tra");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
