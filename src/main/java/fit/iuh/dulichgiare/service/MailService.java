package fit.iuh.dulichgiare.service;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.entity.Booking;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.entity.Tour;
import jakarta.mail.MessagingException;

@Service
public interface MailService {
	public String sendEmailWhenBookingIsSuccess(String from, String to, String subject, String fullName,
			Booking booking, String linkOrderId, String templateMail) throws MessagingException;

	public String sendEmailForUserWhenRequestTourNew(Customer customer, String reasonReject) throws MessagingException;

	public String sendEmailReminderOneDayBeforeForTraveller(Booking booking) throws MessagingException;

	public String sendEmailRequestCreatedTourNotification(Tour tour, Customer customer)throws MessagingException;

}
