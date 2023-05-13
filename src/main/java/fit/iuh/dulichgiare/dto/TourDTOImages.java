package fit.iuh.dulichgiare.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TourDTOImages {
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
    private String itineraryName;
}
