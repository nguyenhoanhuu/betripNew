package fit.iuh.dulichgiare.twilio;

import org.springframework.stereotype.Service;

import com.twilio.exception.TwilioException;

@Service
public class SenderTwilio {

    private final TwilioMessageSender twilioMessageSender;

    public SenderTwilio(TwilioMessageSender twilioMessageSender) {
        this.twilioMessageSender = twilioMessageSender;
    }

    public void sendReminder(String phoneNumber, String message) throws TwilioException {
        twilioMessageSender.sendMessage(phoneNumber, message);
    }
}