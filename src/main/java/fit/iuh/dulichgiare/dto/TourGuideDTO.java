package fit.iuh.dulichgiare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourGuideDTO {
    private long id;
    private boolean active;
    private String name;
    private String address;
    private String phone;
    private String email;

}
