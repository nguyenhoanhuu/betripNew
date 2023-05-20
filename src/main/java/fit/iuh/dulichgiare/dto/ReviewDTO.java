package fit.iuh.dulichgiare.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewDTO {
	private long id;
	private String comment;
	private LocalDate reviewDate;
	private int rating;
	private long tourId;
}
