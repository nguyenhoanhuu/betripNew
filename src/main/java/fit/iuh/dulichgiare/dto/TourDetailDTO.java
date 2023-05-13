package fit.iuh.dulichgiare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDetailDTO {

    private long id;
    private String description;
    private String transport;
    private int starHotel;
    private long tourId;

    public TourDetailDTO(String description, String transport, int starHotel, long tourId) {
        super();
        this.description = description;
        this.transport = transport;
        this.starHotel = starHotel;
        this.tourId = tourId;
    }

    /**
     * @param id
     * @param description
     * @param transport
     * @param starHotel
     */
    public TourDetailDTO(long id, String description, String transport, int starHotel) {
        super();
        this.id = id;
        this.description = description;
        this.transport = transport;
        this.starHotel = starHotel;
    }

}
