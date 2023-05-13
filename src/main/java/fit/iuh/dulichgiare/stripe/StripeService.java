package fit.iuh.dulichgiare.stripe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.Charge;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String API_SECET_KEY;

    public String createCharge(String email, String token, double amount) {
        Charge charge = null;
        int convertAmount = (int) amount;
        try {
            Stripe.apiKey = API_SECET_KEY;

            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("description", "Charge for " + email);
            chargeParams.put("currency", "VND");
            chargeParams.put("amount", convertAmount);
            chargeParams.put("source", token);

            charge = Charge.create(chargeParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return charge.getId();
    }

}
