package fit.iuh.dulichgiare.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PromotionDTO {
	private long id;
	private String name;
	private double discount;
	private LocalDate endday;
	private long tourId;
   
	
}
