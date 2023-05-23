package fit.iuh.dulichgiare.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.AccountCustomerDTO;
import fit.iuh.dulichgiare.dto.AccountDTO;
import fit.iuh.dulichgiare.entity.Account;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.entity.Employee;
import fit.iuh.dulichgiare.repository.CustomerRepository;
import fit.iuh.dulichgiare.repository.EmployeeRepository;
import fit.iuh.dulichgiare.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public Customer createAccountCustomer(AccountCustomerDTO accountCustomerDTO) {
		String passwordEncode = passwordEncoder.encode(accountCustomerDTO.getAccount().getPassword());
		Account account = new Account(accountCustomerDTO.getAccount().getUsername(), passwordEncode, "customer");
		Customer customer = new Customer(account, accountCustomerDTO.getName(), accountCustomerDTO.getPhone(),
				accountCustomerDTO.getEmail());
		customerRepository.save(customer);
		return customer;
	}

	@Override
	public AccountCustomerDTO getCustomerById(long id) throws InterruptedException, ExecutionException {

		Customer customer = customerRepository.findById(id).get();
		AccountDTO account = new AccountDTO(customer.getAccount().getId(), customer.getAccount().getUsername(),
				customer.getAccount().getPassword(), customer.getAccount().getAccountType());
		AccountCustomerDTO customerDTO = new AccountCustomerDTO();
		customerDTO.setId(customer.getId());
		customerDTO.setAccount(account);
		customerDTO.setName(customer.getName());
		customerDTO.setPhone(customer.getPhone());
		customerDTO.setEmail(customer.getEmail());
		return customerDTO;
	}

	@Override
	public long countCustomers() {
		return customerRepository.count();
	}

	@Override
	public List<AccountCustomerDTO> getAllCustomer(long employeeId) {
		List<AccountCustomerDTO> customerDTOs = new ArrayList<>();
		AccountCustomerDTO customerDTO = new AccountCustomerDTO();
		Employee employee = employeeRepo.findById(employeeId).get();
		if (employee == null) {
			customerDTO.setResult(0);
			customerDTOs.add(customerDTO);
		}
		List<Customer> customers = customerRepository.findAll();
		for (Customer customer : customers) 
		{AccountCustomerDTO customerDTOFor = new AccountCustomerDTO();
		customerDTOFor.setId(customer.getId());
		customerDTOFor.setName(customer.getName());
		customerDTOFor.setAddress("Việt Nam");
		customerDTOFor.setPhone(customer.getPhone());
		customerDTOFor.setEmail(customer.getEmail());
		customerDTOFor.setResult(1);
			customerDTOs.add(customerDTOFor);
		}
		return customerDTOs;
	}

}
