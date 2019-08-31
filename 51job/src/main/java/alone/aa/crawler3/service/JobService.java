package alone.aa.crawler3.service;

import alone.aa.crawler3.pojo.Job;

import java.util.List;

public interface JobService {
    void save(Job job);

    //根据条件查询
    List<Job>findJob(Job job);
}
