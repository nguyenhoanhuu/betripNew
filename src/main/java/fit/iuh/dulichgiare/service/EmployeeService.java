package fit.iuh.dulichgiare.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.AccountEmployeeDTO;
import fit.iuh.dulichgiare.dto.EmployeeDTO;
import fit.iuh.dulichgiare.entity.Employee;

/**
 * 
 * @author HOAN HUU
 *
 */
@Service
public interface EmployeeService {

	public Employee registerAccountEmployee(AccountEmployeeDTO accountEmployeeDTO)
			throws InterruptedException, ExecutionException;

	public AccountEmployeeDTO getEmployeeById(long id) throws InterruptedException, ExecutionException;

	public EmployeeDTO findEmployeeByAccountId(long id) throws InterruptedException, ExecutionException;
}
