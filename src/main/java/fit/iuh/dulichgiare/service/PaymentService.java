package fit.iuh.dulichgiare.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.PaymentDTO;

@Service
public interface PaymentService {

    public int checkUserBookingIsExisted(String userId, long tourId);

    public int savePayment(PaymentDTO paymentDTO, String userId, String tokenStripe)
            throws InterruptedException, ExecutionException;

    public void automationDeletePaymentStatusOverThreeDay();

    public int updatePayment(PaymentDTO paymentDTO, String userId, String tokenStripe)
            throws InterruptedException, ExecutionException;
}
