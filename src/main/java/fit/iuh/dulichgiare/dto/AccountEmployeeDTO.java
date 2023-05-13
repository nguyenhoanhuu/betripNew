package fit.iuh.dulichgiare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AccountEmployeeDTO {
	private long id;
	private AccountDTO account;
	private String name;
	private String address;
	private String phone;
	private String email;
	
}
