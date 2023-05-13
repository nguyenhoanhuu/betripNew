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

import fit.iuh.dulichgiare.dto.BlogPostDTO;
import fit.iuh.dulichgiare.service.BlogPostService;

@RestController
@RequestMapping("/blogpost")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;

    @GetMapping(value = { "", "/" })
    public List<BlogPostDTO> getAllBlogPostDTO() throws InterruptedException, ExecutionException {
        return blogPostService.getAllBlogPostDTO();
    }

    @PostMapping(value = { "", "/save" })
    public int saveBlogPost(@RequestBody BlogPostDTO blogPostDTO, @AuthenticationPrincipal UserDetails user)
            throws InterruptedException, ExecutionException {
        return blogPostService.saveBlogPost(blogPostDTO, user.getUsername());
    }

    @PostMapping(value = { "", "/update" })
    public int updateBlogPost(@RequestBody BlogPostDTO blogPostDTO, @AuthenticationPrincipal UserDetails user)
            throws InterruptedException, ExecutionException {
        return blogPostService.updateBlogPost(blogPostDTO, user.getUsername());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBlogPost(@PathVariable long id) throws InterruptedException, ExecutionException {
        return blogPostService.deleteBlogPost(id);
    }

    public List<BlogPostDTO> getAllBlogPostByCustomerId(@AuthenticationPrincipal UserDetails user)
            throws InterruptedException, ExecutionException {
        return blogPostService.getAllBlogPostByCustomerId(user.getUsername());
    }
}
