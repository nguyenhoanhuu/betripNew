package fit.iuh.dulichgiare.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.entity.Account;
import fit.iuh.dulichgiare.repository.AccountRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	public static final String USER = "USER";
	public static final String EMPLOYEE = "EMPLOYEE";
	public static final String ROLE_USER = "ROLE_" + USER;
	public static final String ROLE_EMPLOYEE = "ROLE_" + EMPLOYEE;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) {
		final Account account = accountRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

		if (account.getAccountType().contains("employee")) {
			return new JwtUserDetails(account.getId(), username, account.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(ROLE_EMPLOYEE)));
		} else if (account.getAccountType().contains("customer")) {

			return new JwtUserDetails(account.getId(), username, account.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
		}
		return null;
	}

}
