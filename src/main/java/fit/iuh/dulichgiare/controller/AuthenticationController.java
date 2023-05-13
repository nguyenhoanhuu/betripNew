package fit.iuh.dulichgiare.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fit.iuh.dulichgiare.config.JwtTokenService;
import fit.iuh.dulichgiare.config.JwtUserDetailsService;
import fit.iuh.dulichgiare.entity.Account;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.entity.Employee;
import fit.iuh.dulichgiare.repository.AccountRepository;
import fit.iuh.dulichgiare.repository.CustomerRepository;
import fit.iuh.dulichgiare.repository.EmployeeRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private CustomerRepository customerRepo;

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody @Valid final AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(),
                    authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtTokenService.generateToken(userDetails));
        long id = 0;
        Optional<Account> account = accountRepo.findByUsername(authenticationRequest.getLogin());
        if (account.get().getAccountType().equals("employee")) {
            Employee employee = employeeRepo.findEmployeeByPhone(account.get().getUsername());
            id = employee.getId();
        } else if (account.get().getAccountType().equals("customer")) {
            Customer customer = customerRepo.findCustomerByPhone(account.get().getUsername());
            id = customer.getId();
        }
        authenticationResponse.setRole(account.get().getAccountType());
        authenticationResponse.setId(id);
        return authenticationResponse;
    }
}

@Data
class AuthenticationRequest {

    @NotNull
    @Size(max = 255)
    private String login;

    @NotNull
    @Size(max = 255)
    private String password;

}

@Data
class AuthenticationResponse {

    private String accessToken;
    private String role;
    private long id;

}