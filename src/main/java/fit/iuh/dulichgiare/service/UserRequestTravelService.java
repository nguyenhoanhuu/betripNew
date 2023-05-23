package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fit.iuh.dulichgiare.dto.UserRequestTravelDTO;
import jakarta.mail.MessagingException;

public interface UserRequestTravelService {
	public List<UserRequestTravelDTO> getAllRequestTravel() throws InterruptedException, ExecutionException;

	public int saveRequestTravel(UserRequestTravelDTO requestTravelDTO, String userId)
			throws InterruptedException, ExecutionException;

	public int updateRequestTravel(UserRequestTravelDTO requestTravelDTO, String userId)
			throws InterruptedException, ExecutionException;

	public String deleteRequestTravel(long id) throws InterruptedException, ExecutionException;

	public List<UserRequestTravelDTO> getAllRequestTravelByUserId(String userId);

	public void sendMailStatusRequestTourNotification(Long id, String nameCustomer,String reasonReject)throws MessagingException;
	
	public long countUserRequestTravel();
	
}
