package fit.iuh.dulichgiare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDetailDTO {

    private long id;
    private String title;
    private String description;

    public ItineraryDetailDTO(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

}
