package fit.iuh.dulichgiare.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestTravelDTO {

    private long id;
    private boolean active;
    private String departure;
    private String destination;
    private double price;
    private int numberOfPeople;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private LocalDate createAt;
    private LocalDate updateAt;
    private List<Object>itinerarys;
    private long customerId;
    private String customerName;
}
