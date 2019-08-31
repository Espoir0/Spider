package alone.aa.crawler3.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String companyAddr;

    @Column(length = 5000)
    private String companyInfo;
    private String jobName;
    private String jobAddr;

    @Column(length = 5000)
    private String jobInfo;
    private String salary;
    //private Integer salaryMin;
    //private Integer salaryMax;
    private String url;
    private String time;
}
