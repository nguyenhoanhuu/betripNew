package fit.iuh.dulichgiare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourGuideTourDTO {

    private long id;
    private String nameTour;
    private String nameTourGuide;

}
