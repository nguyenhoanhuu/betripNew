package fit.iuh.dulichgiare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	private long id;
	private String username;
	private String password;
	private String accountType;
	public AccountDTO(String username, String password, String accountType) {
		super();
		this.username = username;
		this.password = password;
		this.accountType = accountType;
	}
	
}
