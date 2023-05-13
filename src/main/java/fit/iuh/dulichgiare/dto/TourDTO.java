package fit.iuh.dulichgiare.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class TourDTO {
    private long id;
    private String name;
    private List<String> images;
    private String departureTime;
    private String departure;
    private String destination;
    private LocalDate startDay;
    private LocalDate endDay;
    private int numberOfDay;
    private int numberOfPeople;
    private int subcriber;
    private String type;
    private double price;
    private LocalDateTime createAt;
    private int liked;
    private String createdBy;
    private double promotionPrice;
    private double adultPrice;
    private double childPrice;
    private double babyPrice;
    private PolicyDTO policy;
    private List<TourGuideDTO> tourGuides;
    private TourDetailDTO tourDetail;
    private List<ItineraryDetailDTO> itineraryDetail;

}
