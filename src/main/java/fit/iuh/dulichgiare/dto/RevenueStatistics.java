package fit.iuh.dulichgiare.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class RevenueStatistics {
    private int month;
    private long totalBill;
    private long profit;
}
