package fit.iuh.dulichgiare.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.AccountEmployeeDTO;
import fit.iuh.dulichgiare.dto.EmployeeDTO;
import fit.iuh.dulichgiare.entity.Employee;
import fit.iuh.dulichgiare.service.EmployeeService;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = { "", "/save" })
    public Employee registerAccountEmployee(@RequestBody AccountEmployeeDTO accountEmployeeDTO,
            @AuthenticationPrincipal AuthenticatedPrincipal principhal)
            throws InterruptedException, ExecutionException {
        String name = principhal.getName();

        return employeeService.registerAccountEmployee(accountEmployeeDTO);

    }

    @GetMapping("/{id}")
    public AccountEmployeeDTO getEmployeeById(@PathVariable long id) throws InterruptedException, ExecutionException {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/searchaccount/{id}")
    public EmployeeDTO findEmployeeByAccountId(@PathVariable long id) throws InterruptedException, ExecutionException {
        return employeeService.findEmployeeByAccountId(id);
    }
}
