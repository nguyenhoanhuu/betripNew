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

import fit.iuh.dulichgiare.dto.UserRequestTravelDTO;
import fit.iuh.dulichgiare.respone.MessageResponse;
import fit.iuh.dulichgiare.service.UserRequestTravelService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/requesttravel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserRequestTravelController {

	@Autowired
	private UserRequestTravelService userRequestTravelService;

	@GetMapping(value = { "", "/" })
	public List<UserRequestTravelDTO> getAllRequestTravel() throws InterruptedException, ExecutionException {
		return userRequestTravelService.getAllRequestTravel();
	}

	@PostMapping(value = { "", "/save" })
	public ResponseEntity<MessageResponse> saveRequestTravel(@RequestBody UserRequestTravelDTO requestTravelDTO,
			@AuthenticationPrincipal UserDetails user) throws InterruptedException, ExecutionException {
		MessageResponse messageResponse = new MessageResponse();
		if (user == null) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Vui lòng đăng nhập để gửi yêu cầu tạo tour");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		}
		int result = userRequestTravelService.saveRequestTravel(requestTravelDTO, user.getUsername());
		if (result == 0) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Vui lòng đăng nhập để gửi yêu cầu tạo tour");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		} else if (result == 1) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Điểm đi không được để trống");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		} else if (result == 2) {
			messageResponse.setStatus(false);
			messageResponse.setMessage("Điểm đến không được để trống");
			return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
		} else if (result == 3) {
			messageResponse.setStatus(true);
			messageResponse.setMessage("Yêu cầu đặt tour thành công, xin vui lòng đợi phản hồi từ chúng tôi");
			return new ResponseEntity<>(messageResponse, HttpStatus.OK);
		}
		return null;
	}

	@PostMapping(value = { "", "/update" })
	public int updateRequestTravel(@RequestBody UserRequestTravelDTO requestTravelDTO,
			@AuthenticationPrincipal UserDetails user) throws InterruptedException, ExecutionException {
		return userRequestTravelService.updateRequestTravel(requestTravelDTO, user.getUsername());

	}

	@DeleteMapping("/delete/{id}")
	public String deleteRequestTravel(@PathVariable long id) throws InterruptedException, ExecutionException {
		return userRequestTravelService.deleteRequestTravel(id);
	}

	@GetMapping("/customer")
	public List<UserRequestTravelDTO> getAllRequestTravelByUserId(@AuthenticationPrincipal UserDetails user) {
		return userRequestTravelService.getAllRequestTravelByUserId(user.getUsername());
	}

	@GetMapping("/statusRequestTour")
	public String sendMailStatusRequestTourNotification(@RequestParam Long id, @RequestParam String customerName,
			@RequestParam String reasonReject) throws MessagingException {
		userRequestTravelService.sendMailStatusRequestTourNotification(id, customerName, reasonReject);
		return "oke";
	}
}
