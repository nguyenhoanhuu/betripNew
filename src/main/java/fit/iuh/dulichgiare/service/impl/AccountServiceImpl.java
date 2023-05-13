package fit.iuh.dulichgiare.service.impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.AccountDTOUI;
import fit.iuh.dulichgiare.entity.Account;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.entity.Employee;
import fit.iuh.dulichgiare.repository.AccountRepository;
import fit.iuh.dulichgiare.repository.CustomerRepository;
import fit.iuh.dulichgiare.repository.EmployeeRepository;
import fit.iuh.dulichgiare.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public Account getAccountById(long id) throws InterruptedException, ExecutionException {
        Optional<Account> account = accountRepo.findById(id);
        if (account.isPresent()) {
            return account.get();
        }
        return null;
    }

    @Override
    public AccountDTOUI getAccountByUserName(String userName) throws InterruptedException, ExecutionException {
        AccountDTOUI accountDTOUI = new AccountDTOUI();
        Optional<Account> account = accountRepo.findByUsername(userName);
        if (account.isPresent() && account.get().getAccountType().contains("employee")) {
            Employee employee = employeeRepo.findEmployeeByPhone(account.get().getUsername());
            accountDTOUI.setId(employee.getId());
            accountDTOUI.setName(employee.getName());
            accountDTOUI.setPhone(employee.getPhone());
            accountDTOUI.setEmail(employee.getEmail());
            accountDTOUI.setAddress(employee.getAddress());
        }
        if (account.isPresent() && account.get().getAccountType().contains("customer")) {
            Customer customer = customerRepo.findCustomerByPhone(account.get().getUsername());
            accountDTOUI.setId(customer.getId());
            accountDTOUI.setName(customer.getName());
            accountDTOUI.setPhone(customer.getPhone());
            accountDTOUI.setEmail(customer.getEmail());
        }
        return accountDTOUI;
    }

}
