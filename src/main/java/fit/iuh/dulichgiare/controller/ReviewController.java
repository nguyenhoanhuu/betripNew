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

import fit.iuh.dulichgiare.dto.ReviewDTO;
import fit.iuh.dulichgiare.respone.MessageResponse;
import fit.iuh.dulichgiare.service.ReviewService;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@GetMapping(value = { "", "/" })
	public List<ReviewDTO> getAllReview() throws InterruptedException, ExecutionException {
		return reviewService.getAllReview();
	}

	@GetMapping(value = { "/list" })
	public List<ReviewDTO> getAllReviewByTourId(@RequestParam long tourId)
			throws InterruptedException, ExecutionException {
		return reviewService.getAllReviewByTourId(tourId);
	}

	@PostMapping(value = { "/save" })
	public ResponseEntity<MessageResponse> saveReview(@RequestBody ReviewDTO reviewDTO,
			@AuthenticationPrincipal UserDetails user) throws InterruptedException, ExecutionException {
		MessageResponse messageResponse = new MessageResponse();
		if (user == null) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Vui lòng đăng nhập để đánh giá tour!");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		}
		int result = reviewService.saveReview(reviewDTO, user.getUsername());
		if (result == 1) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Người dùng không có quyền đánh giá tour!");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		} else if (result == 3) {
			messageResponse.setStatus(false);
			messageResponse.setMessage(" Đánh giá tour thành công!");
			return new ResponseEntity<>(messageResponse, HttpStatus.OK);
		} else if (result == 2 || result == 4) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Bạn không thể đánh giá tour này!");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		} else {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Đánh giá tour phải sau ngày kết thúc tour!");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = { "", "/update" })
	public int updateReview(@RequestBody ReviewDTO reviewDTO, @AuthenticationPrincipal UserDetails user)
			throws InterruptedException, ExecutionException {
		return reviewService.updateReview(reviewDTO, user.getUsername());
	}

	@DeleteMapping("/delete/{id}")
	public String deleteReviewDTO(@PathVariable long id, @AuthenticationPrincipal UserDetails user)
			throws InterruptedException, ExecutionException {
		return reviewService.deleteReviewDTO(id, user.getUsername());
	}

	@GetMapping("/check")
	public int check(@RequestParam long customerId, @RequestParam long tourId) {
		return reviewService.checkCustomerPermissionReviewTour(customerId, tourId);
	}

}
