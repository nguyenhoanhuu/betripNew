package fit.iuh.dulichgiare.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePayment {
    @NotNull
    @Min(4)
    private Integer amount;

    @NotNull
    @Size(min = 5, max = 200)
    private String featureRequest;
}
