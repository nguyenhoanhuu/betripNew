package fit.iuh.dulichgiare.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.ItineraryDetailDTO;
import fit.iuh.dulichgiare.service.ItineraryDetailService;

@RestController
@RequestMapping("/itinerarydetails")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItineraryDetailController {
	@Autowired
	private ItineraryDetailService itineraryDetailService;

	@GetMapping(value = { "", "/" })
	public List<ItineraryDetailDTO> getAllItineraryDetails() throws InterruptedException, ExecutionException {
		return itineraryDetailService.getAllItineraryDetails();
	}

	@PostMapping(value = { "", "/save" })
	public int saveItineraryDetail(@RequestBody ItineraryDetailDTO itineraryDetailDTO)
			throws InterruptedException, ExecutionException {
		return itineraryDetailService.saveItineraryDetail(itineraryDetailDTO);
	}

	@PostMapping(value = { "", "/update" })
	public int updateItineraryDetail(@RequestBody ItineraryDetailDTO itineraryDetailDTO)
			throws InterruptedException, ExecutionException {
		return itineraryDetailService.updateItineraryDetail(itineraryDetailDTO);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteItineraryDetail(@PathVariable long id) throws InterruptedException, ExecutionException {
		return itineraryDetailService.deleteItineraryDetail(id);
	}

	@GetMapping("/search/{id}")
	public List<ItineraryDetailDTO> findItineraryDetailByItineraryId(@PathVariable long id)
			throws InterruptedException, ExecutionException {
		return itineraryDetailService.findItineraryDetailByItineraryId(id);
	}
}
