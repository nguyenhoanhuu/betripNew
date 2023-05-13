package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.ItineraryDetailDTO;

@Service
public interface ItineraryDetailService {

	public List<ItineraryDetailDTO> getAllItineraryDetails() throws InterruptedException, ExecutionException;

	public int saveItineraryDetail(ItineraryDetailDTO itineraryDetailDTO)
			throws InterruptedException, ExecutionException;

	public int updateItineraryDetail(ItineraryDetailDTO itineraryDetailDTO)
			throws InterruptedException, ExecutionException;

	public String deleteItineraryDetail(long id) throws InterruptedException, ExecutionException;

	public ItineraryDetailDTO getItineraryDetailById(long id) throws InterruptedException, ExecutionException;

	public List<ItineraryDetailDTO> findItineraryDetailByItineraryId(long id)
			throws InterruptedException, ExecutionException;

}
