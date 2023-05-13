package fit.iuh.dulichgiare.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HotelControllerDemo {

	@GetMapping(value = { "", "/" })
	public String getAllHotels() throws InterruptedException, ExecutionException {
		return "Hello world!";
	}

	@PreAuthorize("hasAuthority('EMPLOYEE')")
	@GetMapping(value = { "/get1" })
	public String getAllHotels1() throws InterruptedException, ExecutionException {
		return "Hello world!1";
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping(value = { "/get2" })
	public String getAllHotels2() throws InterruptedException, ExecutionException {
		return "Hello world2!";
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping(value = { "/get3" })
	public String getAllHotels3() throws InterruptedException, ExecutionException {
		return "Hello world3!";
	}
}
