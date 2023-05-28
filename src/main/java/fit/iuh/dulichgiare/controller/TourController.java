package fit.iuh.dulichgiare.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.DestinationCount;
import fit.iuh.dulichgiare.dto.ResponseFavoriteDestination;
import fit.iuh.dulichgiare.dto.TourDTO;
import fit.iuh.dulichgiare.dto.TourDTOImages;
import fit.iuh.dulichgiare.dto.TourDTOSave;
import fit.iuh.dulichgiare.respone.MessageResponse;
import fit.iuh.dulichgiare.service.TourService;

@RestController
@RequestMapping("/tours")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TourController {

	@Autowired
	private TourService tourService;

	@GetMapping(value = { "", "/" })
	public ResponseEntity<List<TourDTOImages>> getAllTours(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize, @RequestParam String sortBy)
			throws InterruptedException, ExecutionException {
		return new ResponseEntity<List<TourDTOImages>>(tourService.getAllTours(pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

	@PostMapping(value = { "/save" })
	public ResponseEntity<MessageResponse> saveTour(@RequestBody TourDTOSave tourDTOSave,
			@AuthenticationPrincipal UserDetails user) throws InterruptedException, ExecutionException {
		MessageResponse messageResponse = new MessageResponse();

		int result = tourService.saveTour(tourDTOSave, user.getUsername());

		if (result == 0) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Bạn không có quyền thực hiện!");
			return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
		} else if (result == 1) {
			messageResponse.setStatus(true);
			messageResponse.setMessage("Lưu tour thành công");
			return new ResponseEntity<>(messageResponse, HttpStatus.OK);
		} else if (result == 3) {
			messageResponse.setStatus(true);
			messageResponse.setMessage("Tour đã tồn tại ");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	@PostMapping(value = { "/saveRequestTour" })
	public ResponseEntity<MessageResponse> saveTourWhenRequestApproval(@RequestBody TourDTOSave tourDTOSave,
			@AuthenticationPrincipal UserDetails user) throws InterruptedException, ExecutionException {
		MessageResponse messageResponse = new MessageResponse();
		int result = tourService.saveTourWhenUserRequestTour(tourDTOSave, user.getUsername());
		if (result == 0) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Bạn không có quyền thực hiện!");
			return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
		} else if (result == 1) {
			messageResponse.setStatus(true);
			messageResponse.setMessage("Tạo tour và gửi email cho khách hàng thành công");
			return new ResponseEntity<>(messageResponse, HttpStatus.OK);
		} else if (result == 3) {
			messageResponse.setStatus(true);
			messageResponse.setMessage("Tour đã tồn tại ");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	@PostMapping(value = { "", "/update" })
	public ResponseEntity<MessageResponse> updateTour(@RequestBody TourDTOSave tourDTOSave,
			@AuthenticationPrincipal UserDetails user) throws InterruptedException, ExecutionException {
		MessageResponse messageResponse = new MessageResponse();
		if (user == null) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Vui lòng đăng nhập với quyền admin!");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		}
		int result = tourService.updateTour(tourDTOSave, user.getUsername());

		if (result == 0) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Bạn không có quyền thực hiện!");
			return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
		} else if (result == 1) {
			messageResponse.setStatus(true);
			messageResponse.setMessage("Cập nhật tour thành công");
			return new ResponseEntity<>(messageResponse, HttpStatus.OK);
		} else if (result == 3) {
			messageResponse.setStatus(true);
			messageResponse.setMessage("Tour đã tồn tại ");
			return new ResponseEntity<>(messageResponse, HttpStatus.OK);
		}
		return null;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<MessageResponse> deleteTour(@PathVariable long id)
			throws InterruptedException, ExecutionException {
		int result = tourService.deleteTour(id);
		MessageResponse messageResponse = new MessageResponse();
		if (result == 0) {
			messageResponse.setStatus(true);
			messageResponse.setMessage("Xoá tour thành công");
			return new ResponseEntity<>(messageResponse, HttpStatus.OK);
		} else {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Tour bạn muốn xoá không tồn tại");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public TourDTO getTourById(@PathVariable long id) throws InterruptedException, ExecutionException {
		return tourService.getTourById(id);
	}

	@GetMapping(value = { "/top/{number}" })
	public List<TourDTOImages> getTopFavoriteTours(@PathVariable int number)
			throws InterruptedException, ExecutionException {
		return tourService.getTopFavoriteTours(number);
	}

	@GetMapping(value = { "/topdestination/{numberFavorite}/{numberDay}" })
	public List<DestinationCount> getTopFavoriteDestinations(@PathVariable int numberFavorite,
			@PathVariable int numberDay) {
		return tourService.getTopFavoriteDestinations(numberFavorite, numberDay);
	}

	@GetMapping("/search/{name}")
	public List<TourDTOImages> getAllToursByName(@PathVariable String name)
			throws InterruptedException, ExecutionException {
		return tourService.getAllToursByName(name);
	}

	@GetMapping("/departureordestinationorpriceortype")
	public ResponseEntity<List<TourDTOImages>> findByDepartureOrDestinationOrPriceOrType(@RequestParam String departure,
			@RequestParam String destination, @RequestParam(defaultValue = "0", required = true) double startPrice,
			@RequestParam(defaultValue = "100000000", required = true) double endPrice, @RequestParam String type,
			@RequestParam(defaultValue = "0", required = true) long numberDays, @RequestParam Integer pageNo,
			@RequestParam Integer pageSize, @RequestParam String sortBy,
			@RequestParam(defaultValue = "0", required = true) int checkPromotion,
			@RequestParam(defaultValue = "0", required = true) int checkSubcriber) {
		return new ResponseEntity<>(tourService.getAlls(departure, destination, startPrice, endPrice, type, pageNo,
				pageSize, sortBy, numberDays, checkPromotion, checkSubcriber), HttpStatus.OK);
	}

	@GetMapping("/top3/toexpire")
	public List<TourDTOImages> getTop3ToursSoonToExpireWithPromotion() {
		return tourService.getTop3ToursSoonToExpireWithPromotion();
	}

	@GetMapping("/top8/favoritedestination")
	public List<ResponseFavoriteDestination> getTop8FavoriteDestinations() {
		return tourService.getTop8FavoriteDestinations();
	}

	@GetMapping("/top3/random")
	public List<TourDTOImages> getRandomTours(@RequestParam String type) {
		return tourService.getRandomTours(type);
	}

	@GetMapping("/northDestination")
	public List<TourDTOImages> getAllTourNorthDestination() {
		return tourService.getAllTourNorthDestination();
	}

	@GetMapping("/centralDestination")
	public List<TourDTOImages> getAllTourCentralDestination() {
		return tourService.getAllTourCentralDestination();
	}

	@GetMapping("/southDestination")
	public List<TourDTOImages> getAllTourSorthDestination() {
		return tourService.getAllTourSorthDestination();
	}

}
