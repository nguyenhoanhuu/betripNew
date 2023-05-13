package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.BlogPostDTO;

@Service
public interface BlogPostService {
    public List<BlogPostDTO> getAllBlogPostDTO() throws InterruptedException, ExecutionException;

    public List<BlogPostDTO> getAllBlogPostByCustomerId(String userId) throws InterruptedException, ExecutionException;

    public int saveBlogPost(BlogPostDTO blogPostDTO, String userId) throws InterruptedException, ExecutionException;

    public int updateBlogPost(BlogPostDTO blogPostDTO, String userId) throws InterruptedException, ExecutionException;

    public String deleteBlogPost(long id) throws InterruptedException, ExecutionException;

}
