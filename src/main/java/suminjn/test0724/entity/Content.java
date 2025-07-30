package suminjn.test0724.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Table(name = "content")
public class Content {

    @Id
    private Long contentId;

    private String title;
    private Integer zipcode;
    private String tel;
    private String contentTypeId;
    private Double mapX;
    private Double mapY;
    private String addr;
    private String firstimage;
    private String firstimage2;
    private Integer areacode;
    private Integer sigungucode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
