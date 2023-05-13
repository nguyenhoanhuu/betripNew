package fit.iuh.dulichgiare.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.constant.Constants;
import fit.iuh.dulichgiare.dto.UserRequestTravelDTO;
import fit.iuh.dulichgiare.entity.Account;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.entity.Employee;
import fit.iuh.dulichgiare.entity.UserRequestTravel;
import fit.iuh.dulichgiare.repository.AccountRepository;
import fit.iuh.dulichgiare.repository.CustomerRepository;
import fit.iuh.dulichgiare.repository.EmployeeRepository;
import fit.iuh.dulichgiare.repository.UserRequestTravelRepository;
import fit.iuh.dulichgiare.service.MailService;
import fit.iuh.dulichgiare.service.UserRequestTravelService;
import jakarta.mail.MessagingException;

@Service
public class UserRequestTravelServiceImpl implements UserRequestTravelService {

	@Autowired
	private UserRequestTravelRepository userRequestTravelRepo;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private MailService mailService;

	@Override
	public List<UserRequestTravelDTO> getAllRequestTravel() throws InterruptedException, ExecutionException {
		return userRequestTravelRepo.findAll(Sort.by("id").descending()).stream().map(requestTravel -> {
			UserRequestTravelDTO reqTravelDTO = new UserRequestTravelDTO();
			reqTravelDTO.setId(requestTravel.getId());
			reqTravelDTO.setDeparture(requestTravel.getDeparture());
			reqTravelDTO.setDestination(requestTravel.getDestination());
			reqTravelDTO.setNumberOfPeople(requestTravel.getNumberofpeople());
			reqTravelDTO.setStartDate(requestTravel.getStartDate());
			reqTravelDTO.setEndDate(requestTravel.getEndDate());
			reqTravelDTO.setCreateAt(requestTravel.getCreateAt());
			reqTravelDTO.setUpdateAt(requestTravel.getUpdateAt());
			reqTravelDTO.setPrice(requestTravel.getPrice());
			reqTravelDTO.setType(requestTravel.getType());
			reqTravelDTO.setStatus(requestTravel.getStatus());
			reqTravelDTO.setCustomerId(requestTravel.getCustomer().getId());
			reqTravelDTO.setCustomerName(requestTravel.getCustomer().getName());
			reqTravelDTO.setItinerarys(requestTravel.getItineraryTravels());
			return reqTravelDTO;
		}).toList();
	}

	@Override
	public int saveRequestTravel(UserRequestTravelDTO requestTravelDTO, String userId)
			throws InterruptedException, ExecutionException {
		Customer customer = customerRepo.findCustomerByPhone(userId);
		if (!checkUser(userId)) {
			return 0;
		}
		if (requestTravelDTO.getDeparture().equals("")) {
			return 1;
		}
		if (requestTravelDTO.getDestination().equals("")) {
			return 2;
		}
		if (requestTravelDTO != null) {
			UserRequestTravel userRequestTravel = new UserRequestTravel();
			userRequestTravel.setActive(true);
			userRequestTravel.setDeparture(requestTravelDTO.getDeparture());
			userRequestTravel.setDestination(requestTravelDTO.getDestination());
			userRequestTravel.setNumberofpeople(requestTravelDTO.getNumberOfPeople());
			userRequestTravel.setStartDate(requestTravelDTO.getStartDate());
			userRequestTravel.setEndDate(requestTravelDTO.getEndDate());
			userRequestTravel.setCreateAt(LocalDate.now());
			userRequestTravel.setPrice(requestTravelDTO.getPrice());
			userRequestTravel.setType(requestTravelDTO.getType());
			userRequestTravel.setStatus(Constants.STATUS_CHO_XAC_NHAN);
			userRequestTravel.setCustomer(customer);
			userRequestTravel.setItineraryTravels(requestTravelDTO.getItinerarys());
			userRequestTravel.setItineraryTravels(requestTravelDTO.getItinerarys());
			userRequestTravelRepo.save(userRequestTravel);
			return 3;
		}
		return 4;
	}

	@Override
	public int updateRequestTravel(UserRequestTravelDTO requestTravelDTO, String userId)
			throws InterruptedException, ExecutionException {
		UserRequestTravel userRequestTravel = new UserRequestTravel();
		if (requestTravelDTO != null) {
			Optional<Account> account = accountRepo.findByUsername(userId);
			if (account.get().getAccountType().equals("employee")) {
				Customer customer = customerRepo.findById(requestTravelDTO.getCustomerId()).get();
				userRequestTravel.setCustomer(customer);
				userRequestTravel.setStatus("Chấp nhận");
				userRequestTravel.setUpdateAt(LocalDate.now());
			} else {
				Customer customer = customerRepo.findCustomerByPhone(userId);
				userRequestTravel.setCustomer(customer);
			}
			userRequestTravel.setId(requestTravelDTO.getId());
			userRequestTravel.setDeparture(requestTravelDTO.getDeparture());
			userRequestTravel.setDestination(requestTravelDTO.getDestination());
			userRequestTravel.setNumberofpeople(requestTravelDTO.getNumberOfPeople());
			userRequestTravel.setStartDate(requestTravelDTO.getStartDate());
			userRequestTravel.setEndDate(requestTravelDTO.getEndDate());
			userRequestTravel.setCreateAt(requestTravelDTO.getCreateAt());
			userRequestTravel.setPrice(requestTravelDTO.getPrice());
			userRequestTravel.setType(requestTravelDTO.getType());
			userRequestTravelRepo.save(userRequestTravel);
			return 1;
		}
		return 0;
	}

	@Override
	public String deleteRequestTravel(long id) throws InterruptedException, ExecutionException {
		Optional<UserRequestTravel> userRequestTravel = userRequestTravelRepo.findById(id);
		if (userRequestTravel.isPresent()) {
			userRequestTravel.orElseThrow().setCustomer(null);
			userRequestTravelRepo.delete(userRequestTravel.get());
			return "Delete success with by id: " + id;
		}
		return "Delete failure with by id: " + id;
	}

	private boolean checkUser(String userId) {
		Optional<Account> account = accountRepo.findByUsername(userId);
		if (account.get().getAccountType().equals("employee")) {
			Employee employee = employeeRepo.findEmployeeByPhone(account.get().getUsername());
			return true;
		} else if (account.get().getAccountType().equals("customer")) {
			Customer customer = customerRepo.findCustomerByPhone(account.get().getUsername());
			return true;
		}
		return false;
	}

	@Override
	public List<UserRequestTravelDTO> getAllRequestTravelByUserId(String userId) {
		Customer customer = customerRepo.findCustomerByPhone(userId);
		if (customer != null) {
			return userRequestTravelRepo.findAllByCustomerId(customer.getId()).stream().map(requestTravel -> {
				UserRequestTravelDTO reqTravelDTO = new UserRequestTravelDTO();
				reqTravelDTO.setId(requestTravel.getId());
				reqTravelDTO.setDeparture(requestTravel.getDeparture());
				reqTravelDTO.setDestination(requestTravel.getDestination());
				reqTravelDTO.setNumberOfPeople(requestTravel.getNumberofpeople());
				reqTravelDTO.setStartDate(requestTravel.getStartDate());
				reqTravelDTO.setEndDate(requestTravel.getEndDate());
				reqTravelDTO.setCreateAt(requestTravel.getCreateAt());
				reqTravelDTO.setPrice(requestTravel.getPrice());
				reqTravelDTO.setType(requestTravel.getType());
				reqTravelDTO.setCustomerId(requestTravel.getCustomer().getId());
				reqTravelDTO.setCustomerName(requestTravel.getCustomer().getName());
				return reqTravelDTO;
			}).toList();
		}
		return null;
	}

	@Override
	public void sendMailStatusRequestTourNotification(Long id, String customerName, String reasonReject)
			throws MessagingException {
		Customer customer = customerRepo.findCustomerByName(customerName);
		UserRequestTravel userRequestTravel = userRequestTravelRepo.findById(id).get();
		if (userRequestTravel != null) {
			if(reasonReject.equals("")) {
				userRequestTravel.setStatus(Constants.XAC_NHAN);
				userRequestTravel.setUpdateAt(LocalDate.now());
				userRequestTravelRepo.save(userRequestTravel);
			}else {
				userRequestTravel.setStatus(Constants.TU_CHOI);
				userRequestTravel.setUpdateAt(LocalDate.now());
				userRequestTravelRepo.save(userRequestTravel);
			}
		}
		CompletableFuture.runAsync(() -> {
			try {
				mailService.sendEmailForUserWhenRequestTourNew(customer, reasonReject);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

}
