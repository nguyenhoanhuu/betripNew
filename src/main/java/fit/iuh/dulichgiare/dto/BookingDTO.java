package fit.iuh.dulichgiare.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class BookingDTO {
    private long id;
    private boolean active;
    private LocalDate startDayTour;
    private LocalDate endDayTour;
    private String departureTime;
    private String nameCustomer;
    private long tourId;
    private String nameTour;
    private double priceTour;
    private double priceVoucher;
    private String voucherCode;
    private int numberOfAdbult;
    private int numberOfChildren;
    private List<Object> infoOfAdbult;
    private List<Object> infoOfChildren;
    private LocalDateTime createAt;
    private double total;
    private String note;
    private String status;
    private PaymentDTO payment;
    private int result;

}
