package fit.iuh.dulichgiare.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.ReviewDTO;
import fit.iuh.dulichgiare.entity.Booking;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.entity.Review;
import fit.iuh.dulichgiare.repository.BookingRepository;
import fit.iuh.dulichgiare.repository.CustomerRepository;
import fit.iuh.dulichgiare.repository.ReviewRepository;
import fit.iuh.dulichgiare.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private BookingRepository bookingRepo;

    @Override
    public List<ReviewDTO> getAllReview() throws InterruptedException, ExecutionException {
        return reviewRepo.findAll().stream().map(review -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(review.getId());
            dto.setRating(review.getRating());
            dto.setComment(review.getComment());
            dto.setReviewDate(review.getReviewDate());
            dto.setBookingId(review.getBooking().getId());
            return dto;
        }).toList();
    }

    @Override
    public int saveReview(ReviewDTO reviewDTO, String userId) throws InterruptedException, ExecutionException {
        Customer customer = customerRepo.findCustomerByPhone(userId);
        List<Booking> bookings = bookingRepo.findBookingByCustomerId(customer.getId());
        for (Booking booking : bookings) {
            if (booking.getTour().getId() == reviewDTO.getTourId()) {
                if (booking.getTour().getEndday().isAfter(LocalDate.now())) {
                    Review review = new Review();
                    review.setComment(reviewDTO.getComment());
                    review.setRating(reviewDTO.getRating());
                    review.setReviewDate(LocalDate.now());
                    review.setBooking(booking);
                    reviewRepo.save(review);
                    return 1;
                }
            }
        }
        throw new IllegalArgumentException(
                "Tour with ID " + reviewDTO.getTourId() + " not found for customer with ID " + customer.getId());
    }

    @Override
    public int updateReview(ReviewDTO reviewDTO, String userId) throws InterruptedException, ExecutionException {
        Customer customer = customerRepo.findCustomerByPhone(userId);
        List<Booking> bookings = bookingRepo.findBookingByCustomerId(customer.getId());
        for (Booking booking : bookings) {
            if (booking.getTour().getId() == reviewDTO.getTourId()) {
                if (booking.getTour().getEndday().isAfter(LocalDate.now())) {
                    Review review = new Review();
                    review.setId(reviewDTO.getId());
                    review.setComment(reviewDTO.getComment());
                    review.setRating(reviewDTO.getRating());
                    review.setReviewDate(LocalDate.now());
                    review.setBooking(booking);
                    reviewRepo.save(review);
                    return 1;
                }
            }
        }
        throw new IllegalArgumentException(
                "Tour with ID " + reviewDTO.getTourId() + " not found for customer with ID " + customer.getId());
    }

    @Override
    public String deleteReviewDTO(long id, String userId) throws InterruptedException, ExecutionException {
        Customer customer = customerRepo.findCustomerByPhone(userId);
        Review review = reviewRepo.findById(id).get();
        if (review.getBooking().getCustomer().getId() == (customer.getId())) {
            review.setBooking(null);
            reviewRepo.delete(review);
            return "Success delete review by id: " + id;
        }
        return "You don't have to permission delete review id: " + id;
    }

}
