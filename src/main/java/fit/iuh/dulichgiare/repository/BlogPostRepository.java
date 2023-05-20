package fit.iuh.dulichgiare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.iuh.dulichgiare.entity.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findAllByCustomerId(long id);

//    @Query(value = "DELETE FROM blog_post WHERE id = ?1", nativeQuery = true)
//    void deleteBlogPostById(long id);

}
