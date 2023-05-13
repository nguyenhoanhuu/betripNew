package fit.iuh.dulichgiare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

	private long id;
	private long accountId;
	private String name;
	private String address;
	private String phone;
	private String email;
}
