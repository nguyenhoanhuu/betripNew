package fit.iuh.dulichgiare.service.impl;

import java.time.format.DateTimeFormatter;

import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import fit.iuh.dulichgiare.constant.Constants;
import fit.iuh.dulichgiare.entity.Booking;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private JavaMailSender emailSender;

	@Override
	public String sendEmailWhenBookingIsSuccess(String from, String to, String subject, String fullName,
			Booking booking, String linkOrderId) throws MessagingException {
		MimeMessage mimeMessage = this.emailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		String departureTime = booking.getDepartureTime() + " " + formatter.format(booking.getStartDayTour());
		String timeTour = booking.getTour().getNumberofday() + "N" + (booking.getTour().getNumberofday() - 1) + "Đ";
		String body = getContextEmail(fullName, booking.getTour().getName(), departureTime, timeTour,
				booking.getNumberofadbult() + booking.getNumberofchildren(), Double.toString(booking.getTotal()),
				linkOrderId, booking.getStatus(), null, Constants.BOOKING_TEMPLATE);
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body, true);
		emailSender.send(mimeMessage);
		return "oke";
	}

	private String getContextEmail(String fullName, String nameTour, String departureTime, String timeTour,
			int volumeCustomer, String totalBill, String linkOrderId, String status, String reasonReject,
			String template) {
		Context ctx = new Context();
		MimeMessage message = emailSender.createMimeMessage();
		ctx.setVariable("fullName", fullName);
		ctx.setVariable("nameTour", nameTour);
		ctx.setVariable("departureTime", departureTime);
		ctx.setVariable("timeTour", timeTour);
		ctx.setVariable("volumeCustomer", volumeCustomer);
		ctx.setVariable("totalBill", totalBill);
		ctx.setVariable("status", status);
		ctx.setVariable("linkOrderId", linkOrderId);
		ctx.setVariable("reasonReject", reasonReject);
		return templateEngine.process(template, ctx);
	}

	@Override
	public String sendEmailForUserWhenRequestTourNew(Customer customer, String reasonReject)
			throws MessagingException {
		MimeMessage mimeMessage = this.emailSender.createMimeMessage();
		String body;
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		message.setFrom(Constants.MAIL_SENDER);
//		message.setTo(customer.getEmail());
		message.setTo("hoanhuudev@gmail.com");
		if (!reasonReject.equals("")) {
			message.setSubject(Constants.REQUEST_TRAVEL_REJECTED);
			body = getContextEmail(customer.getName(), null, null, null, 0, null, null, null, reasonReject,
					Constants.REQUEST_TRAVEL_REJECTED_TEMPLATE);

		} else {
			message.setSubject(Constants.REQUEST_TRAVEL_APPROVAL);
			body = getContextEmail(customer.getName(), null, null, null, 0, null, null, null, reasonReject,
					Constants.REQUEST_TRAVEL_APPROVAL_TEMPLATE);
		}
		message.setText(body, true);
		emailSender.send(mimeMessage);
		return "oke";
	}

}
