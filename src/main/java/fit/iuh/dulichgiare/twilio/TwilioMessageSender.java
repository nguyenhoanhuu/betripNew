package fit.iuh.dulichgiare.twilio;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;

import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.IncomingPhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioMessageSender {
    @Value("${twilio.account.sid}")
    private String accountSid;
    @Value("${twilio.auth.token}")
    private String authToken;
    private final TwilioRestClient twilioRestClient;
    private final String twilioNumber;

    public TwilioMessageSender(TwilioRestClient twilioRestClient, String twilioNumber) {
        this.twilioRestClient = twilioRestClient;
        this.twilioNumber = twilioNumber;
    }

    public void sendMessage(String to, String message) throws TwilioException {
        Message.creator(new PhoneNumber(to), new PhoneNumber(twilioNumber), message).create(twilioRestClient);
    }

    public void addPhone(String phoneNumber) {
        Twilio.init(accountSid, authToken);
        try {
            // Create a new phone number with Twilio
//            IncomingPhoneNumber incomingPhoneNumber = IncomingPhoneNumber.creator(new PhoneNumber("+84" + phoneNumber))
//                    .setVoiceUrl("http://demo.twilio.com/docs/voice.xml")
//                    .create();
            IncomingPhoneNumber incomingPhoneNumber = IncomingPhoneNumber.creator(
                    new com.twilio.type.PhoneNumber("+84968172177"))
                .setVoiceUrl(URI.create("http://demo.twilio.com/docs/voice.xml"))
                .create();

            System.out.println(incomingPhoneNumber.getSid());

            // Print the phone number details
            System.out.println("Phone number added successfully: " + incomingPhoneNumber.getPhoneNumber());
        } catch (Exception ex) {
            System.err.println("Failed to add phone number: " + ex.getMessage());
        }
    }
}