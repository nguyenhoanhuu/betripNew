package fit.iuh.dulichgiare.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TourInformationDTO {
    private long id;
    private String nameTour;
    private List<String> images;
    private String departure;
    private String destination;
    private Date startday;
    private int numberofday;
    private int numberofpeople;
    private int subcriber;
    private String type;
    private double price;
    private Date createat;
    private int liked;
    private Double discount;
    private Date endDayPromotion;
    private TourGuideDTO tourGuide;
    private String description;
    private String transport;
    private int starhotel;
    private List<ItineraryDetailDTO> itineraryDetail;

}
