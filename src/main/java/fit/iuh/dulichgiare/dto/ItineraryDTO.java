package fit.iuh.dulichgiare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDTO {
	private long id;
	private long tourId;
	private String description;

	public ItineraryDTO(long tourId, String description) {
		super();
		this.tourId = tourId;
		this.description = description;
	}

}
