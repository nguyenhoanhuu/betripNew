package fit.iuh.dulichgiare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.exception.TwilioException;

import fit.iuh.dulichgiare.twilio.TwilioMessageSender;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/sms")
public class TestTwilio {

    private final TwilioMessageSender twilioMessageSender;

    public TestTwilio(TwilioMessageSender twilioMessageSender) {
        this.twilioMessageSender = twilioMessageSender;
    }

//    @PostMapping
    public ResponseEntity<String> sendSms(@RequestParam String to) {
        try {
            String message = "Chúc mừng! Đặt tour thành công!\r\n" + "\r\n" + "Tên tour: Sài gòn đà lạt\r\n"
                    + "Ngày khởi hành: 20/04/2023\r\n" + "Thời gian tour: 4N3Đ\r\n" + "Số lượng khách: 10\r\n"
                    + "Tổng chi phí: 5.000.000\r\n" + "Liên hệ nếu cần hỗ trợ.\r\n" + "HappyTrip";
            twilioMessageSender.sendMessage("+" + to, message);
            return ResponseEntity.ok("SMS sent successfully!");
        } catch (TwilioException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send SMS: " + e.getMessage());
        }
    }

    @PostMapping
    public void addPhone(@RequestParam String phoneNumber) {
        twilioMessageSender.addPhone(phoneNumber);
    }

}