package fit.iuh.dulichgiare.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class VoucherDTO {
    private long id;
    private boolean active;
    private String code;
    private double discount;
    private double limit;
    private LocalDate expriedDate;
}
