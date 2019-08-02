package alone.aa.pojo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="top250_book2")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,name = "book_name")
    private String bookName;

    @Column(length = 50,name = "img")
    private String imgName;

    @Column(length = 254,name = "des")
    private String desc;

    @Column(length = 50,name = "hot")
    private String hot;

    @Column(length = 50,name = "person")
    private String person;

    @Column(length = 50,name = "score")
    private String score;

    @CreatedDate
    @Column(name="created")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "updated")
    private Date updateTime;
}

