package fit.iuh.dulichgiare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTOUI {
    private long id;
    private String name;
    private String address;
    private String phone;
    private String email;
}
