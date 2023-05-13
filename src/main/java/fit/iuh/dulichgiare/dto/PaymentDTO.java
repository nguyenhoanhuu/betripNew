package fit.iuh.dulichgiare.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PaymentDTO {
    private long id;
    private String accountInfo;
    private LocalDateTime paymentDate;
    private String status;
    private String type;
    private long bookingId;
}
