package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.ReviewDTO;

@Service
public interface ReviewService {
	public List<ReviewDTO> getAllReview() throws InterruptedException, ExecutionException;

	public int saveReview(ReviewDTO reviewDTO, String userId) throws InterruptedException, ExecutionException;

	public int updateReview(ReviewDTO reviewDTO, String userId) throws InterruptedException, ExecutionException;

	public String deleteReviewDTO(long id, String userId) throws InterruptedException, ExecutionException;

	public List<ReviewDTO> getAllReviewByTourId(long tourId);
	
	public int checkCustomerPermissionReviewTour(long customerId, long tourId);
}
