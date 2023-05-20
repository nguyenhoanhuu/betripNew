package fit.iuh.dulichgiare.respone;

import java.util.List;

import fit.iuh.dulichgiare.dto.AccountCustomerDTO;
import fit.iuh.dulichgiare.dto.BookingDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MessageResponse {
	private boolean status;
	private String message;
	private double price;
	private long bookingId;
	private BookingDTO booking;
	private List<BookingDTO> bookings;
	private List<AccountCustomerDTO> customers;

	/**
	 * @param status
	 * @param message
	 */
	public MessageResponse(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

}
