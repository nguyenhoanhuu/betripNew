package fit.iuh.dulichgiare.service.impl;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.AccountCustomerDTO;
import fit.iuh.dulichgiare.dto.AccountDTO;
import fit.iuh.dulichgiare.entity.Account;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.repository.CustomerRepository;
import fit.iuh.dulichgiare.service.AccountService;
import fit.iuh.dulichgiare.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

}
