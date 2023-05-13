package fit.iuh.dulichgiare.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.BlogPostDTO;
import fit.iuh.dulichgiare.entity.BlogPost;
import fit.iuh.dulichgiare.entity.Customer;
import fit.iuh.dulichgiare.repository.BlogPostRepository;
import fit.iuh.dulichgiare.repository.CustomerRepository;
import fit.iuh.dulichgiare.service.BlogPostService;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepo;
    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public List<BlogPostDTO> getAllBlogPostDTO() throws InterruptedException, ExecutionException {
        List<BlogPost> blogPosts = blogPostRepo.findAll();
        List<BlogPostDTO> blogPostDTOs = new ArrayList<>();
        for (BlogPost blogPost : blogPosts) {
            BlogPostDTO blogPostDTO = new BlogPostDTO();
            blogPostDTO.setId(blogPost.getId());
            blogPostDTO.setTitle(blogPost.getTitle());
            blogPostDTO.setContent(blogPost.getContent());
            blogPostDTO.setPublishDate(blogPost.getPublishDate());
            blogPostDTO.setUpdateAt(blogPost.getUpdateAt());
            blogPostDTO.setCustomerName(blogPost.getCustomer().getName());
            blogPostDTOs.add(blogPostDTO);
        }

        return blogPostDTOs;
    }

    @Override
    public int saveBlogPost(BlogPostDTO blogPostDTO, String userId) throws InterruptedException, ExecutionException {
        Customer customer = customerRepo.findCustomerByPhone(userId);
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(blogPostDTO.getTitle());
        blogPost.setContent(blogPostDTO.getContent());
        blogPost.setPublishDate(LocalDate.now());
        blogPost.setCustomer(customer);
        if (blogPost != null) {
            blogPostRepo.save(blogPost);
            return 1;
        }
        return 0;
    }

    @Override
    public int updateBlogPost(BlogPostDTO blogPostDTO, String userId) throws InterruptedException, ExecutionException {
        Customer customer = customerRepo.findCustomerByPhone(userId);
        BlogPost blogPost = new BlogPost();
        blogPost.setId(blogPostDTO.getId());
        blogPost.setTitle(blogPostDTO.getTitle());
        blogPost.setContent(blogPostDTO.getContent());
        blogPost.setPublishDate(blogPostDTO.getPublishDate());
        blogPost.setUpdateAt(LocalDate.now());
        blogPost.setCustomer(customer);
        if (blogPost != null) {
            blogPostRepo.save(blogPost);
            return 1;
        }
        return 0;
    }

    @Override
    public String deleteBlogPost(long id) throws InterruptedException, ExecutionException {
        if (id > 0) {
            BlogPost post = blogPostRepo.findById(id).orElse(null);
            if (post != null) {
                post.setCustomer(null);
                blogPostRepo.delete(post);
                return "delete success id - " + id;
            }
        }
        return "Delete failed";
    }

    @Override
    public List<BlogPostDTO> getAllBlogPostByCustomerId(String userId) throws InterruptedException, ExecutionException {
        Customer customer = customerRepo.findCustomerByPhone(userId);
        if (customer != null) {
            return blogPostRepo.findAllByCustomerId(customer.getId()).stream().map(blogPost -> {
                BlogPostDTO blogPostDTO = new BlogPostDTO();
                blogPostDTO.setId(blogPost.getId());
                blogPostDTO.setTitle(blogPost.getTitle());
                blogPostDTO.setContent(blogPost.getContent());
                blogPostDTO.setPublishDate(blogPost.getPublishDate());
                blogPostDTO.setUpdateAt(blogPost.getUpdateAt());
                blogPostDTO.setCustomerName(blogPost.getCustomer().getName());
                return blogPostDTO;
            }).toList();
        }

        return null;
    }

}
