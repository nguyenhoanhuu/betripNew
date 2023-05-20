package fit.iuh.dulichgiare.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.AccountCustomerDTO;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.respone.MessageResponse;
import fit.iuh.dulichgiare.service.CustomerService;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping(value = { "", "/register" })
	public Customer registerAccountCustomer(@RequestBody AccountCustomerDTO accountCustomerDTO) {
		return customerService.createAccountCustomer(accountCustomerDTO);
	}

	@GetMapping("/{id}")
	public AccountCustomerDTO getCustomerById1(@PathVariable long id) throws InterruptedException, ExecutionException {
		return customerService.getCustomerById(id);
	}

	@GetMapping("/count")
	public long countCustomers() {
		return customerService.countCustomers();
	}

	@GetMapping("/list")
	public ResponseEntity<MessageResponse> getAllCustomer(@RequestParam long employeeId) {
		MessageResponse messageResponse = new MessageResponse();
		List<AccountCustomerDTO> customers = new ArrayList<>();
		List<AccountCustomerDTO> customerDTOs = customerService.getAllCustomer(employeeId);
		for (AccountCustomerDTO customer : customerDTOs) {
			if (customer.getResult() == 0) {
				messageResponse.setStatus(false);
				messageResponse.setMessage("Bạn không có quyền xem danh sách khách hàng!");
				return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
			} else if (customer.getResult() == 1) {
				messageResponse.setStatus(true);
				messageResponse.setMessage("Danh sách khách hàng!");
				AccountCustomerDTO customerDTO = new AccountCustomerDTO();
				customerDTO.setId(customer.getId());
				customerDTO.setName(customer.getName());
				customerDTO.setAddress("Việt Nam");
				customerDTO.setEmail(customer.getEmail());
				customerDTO.setPhone(customer.getPhone());
				customers.add(customerDTO);
			}
		}
		messageResponse.setCustomers(customers);
		return new ResponseEntity<>(messageResponse, HttpStatus.OK);

	}
}
