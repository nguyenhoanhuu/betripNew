package fit.iuh.dulichgiare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.service.MailService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping()
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestSendMail {

    @Autowired
    private MailService mailService;

//    @GetMapping("/mail")
//    public String sendEmailWhenOrderIsSuccess(String from, String to, String subject, String fullName, String nameTour,
//            String departureTime, String timeTour, String volumeCustomer, String totalBill, String linkOrderId)
//            throws MessagingException  {
//        return mailService.sendEmailWhenOrderIsSuccess("systemhappytrip@gmail.com", "hoanhuudev@gmail.com", "test",
//                "Nguyen Hoan Huu", "Tour vietnam", "20/03/2023","3N4Đ","4","100000", "123/link");
//    }

}
