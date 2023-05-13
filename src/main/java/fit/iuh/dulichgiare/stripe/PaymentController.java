package fit.iuh.dulichgiare.stripe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@CrossOrigin("*")
public class PaymentController {

    @Value("${stripe.public.key}")
    private String API_PUBLIC_KEY;
    @Autowired
    private StripeService stripeService;
    
    @PostMapping("/charge")
    public Response createCharge(@RequestHeader String email, @RequestHeader String token) {
        System.out.println(token);
        if (token == null) {
            return new Response(false, "Stripe payment token is missing. please try again later.");
        }

        String chargeId = stripeService.createCharge(email, token, 20000000);// 9.99 usd

        if (chargeId == null) {
            return new Response(false, "An error accurred while trying to charge.");
        }
        return new Response(true, "Success your charge id is");
    }
}
