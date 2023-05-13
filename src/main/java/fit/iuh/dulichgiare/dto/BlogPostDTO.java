package fit.iuh.dulichgiare.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BlogPostDTO {
    private long id;
    private String title;
    private String content;
    // TODO: No need this field publishDate for save BlogPost
    private LocalDate publishDate;
    // TODO: No need this field updateAt for save BlogPost
    private LocalDate updateAt;
    private String customerName;
}
