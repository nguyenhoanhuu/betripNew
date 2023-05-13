package fit.iuh.dulichgiare.dto;

import fit.iuh.dulichgiare.entity.Voucher;
import lombok.Data;

@Data
public class VoucherCheck {
    private int result;
    private Voucher voucher;
}
