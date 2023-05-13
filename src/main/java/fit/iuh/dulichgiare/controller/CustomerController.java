package fit.iuh.dulichgiare.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.AccountCustomerDTO;
import fit.iuh.dulichgiare.entity.Customer;
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
}
