package fit.iuh.dulichgiare.twilio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.twilio.http.TwilioRestClient;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.number}")
    private String twilioNumber;

    @Bean
    public TwilioRestClient twilioRestClient() {
        return new TwilioRestClient.Builder(accountSid, authToken).build();
    }

    @Bean
    public TwilioMessageSender twilioMessageSender() {
        return new TwilioMessageSender(twilioRestClient(), twilioNumber);
    }
}