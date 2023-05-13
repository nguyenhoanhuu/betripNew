package fit.iuh.dulichgiare.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.ReviewDTO;
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

    @PostMapping(value = { "", "/save" })
    public int saveReview(@RequestBody ReviewDTO reviewDTO, @AuthenticationPrincipal UserDetails user)
            throws InterruptedException, ExecutionException {
        return reviewService.saveReview(reviewDTO, user.getUsername());
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
}
