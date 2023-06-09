package fit.iuh.dulichgiare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCustomerDTO {
	private long id;
	private AccountDTO account;
	private String name;
	private String address;
	private String phone;
	private String email;
	private int result;
}
