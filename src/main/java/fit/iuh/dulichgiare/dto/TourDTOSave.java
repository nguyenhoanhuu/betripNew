package fit.iuh.dulichgiare.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TourDTOSave implements Serializable {
    private long id;
    private String name;
    private String image;
    private String departureTime;
    private String departure;
    private String destination;
    private LocalDate startDay;
    private LocalDate endDay;
    private int numberOfDay;
    private int numberOfPeople;
    private String type;
    private double price;
    private int liked;
    private String createdBy;
    private List<String> tourGuideName;
    private String policyName;
    private String promotionName;
    private TourDetailDTO tourDetail;
    private List<ItineraryDetailDTO> itineraryDetail;
    private String customerName;

}
