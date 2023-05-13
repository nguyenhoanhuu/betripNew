package fit.iuh.dulichgiare.service.impl;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.AccountDTO;
import fit.iuh.dulichgiare.dto.AccountEmployeeDTO;
import fit.iuh.dulichgiare.dto.EmployeeDTO;
import fit.iuh.dulichgiare.entity.Account;
import fit.iuh.dulichgiare.entity.Employee;
import fit.iuh.dulichgiare.repository.EmployeeRepository;
import fit.iuh.dulichgiare.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Employee registerAccountEmployee(AccountEmployeeDTO accountEmployeeemployeeDTO)
            throws InterruptedException, ExecutionException {
        String passwordEncode = passwordEncoder.encode(accountEmployeeemployeeDTO.getAccount().getPassword());
        Account account = new Account(accountEmployeeemployeeDTO.getAccount().getUsername(), passwordEncode,
                "employee");
        Employee employee = new Employee(account, accountEmployeeemployeeDTO.getName(),
                accountEmployeeemployeeDTO.getAddress(), accountEmployeeemployeeDTO.getPhone(),
                accountEmployeeemployeeDTO.getEmail());
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public AccountEmployeeDTO getEmployeeById(long id) throws InterruptedException, ExecutionException {

        Employee employee = employeeRepository.findById(id).get();
        AccountDTO account = new AccountDTO();
        account.setId(employee.getAccount().getId());
        account.setUsername(employee.getAccount().getUsername());
        account.setPassword(employee.getAccount().getPassword());
        account.setAccountType(employee.getAccount().getAccountType());
        AccountEmployeeDTO employeeDTO = new AccountEmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setAccount(account);
        employeeDTO.setEmail(employee.getEmail());
        return employeeDTO;
    }

    @Override
    public EmployeeDTO findEmployeeByAccountId(long id) throws InterruptedException, ExecutionException {
        Employee employee = employeeRepository.findByAccountId(id);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setAccountId(employee.getAccount().getId());
        return employeeDTO;
    }

}
