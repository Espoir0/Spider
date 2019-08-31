package alone.aa.crawler3.dao;

import alone.aa.crawler3.pojo.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDao extends JpaRepository<Job,Long>{
}
