package alone.aa.crawler3.service.impl;

import alone.aa.crawler3.dao.JobDao;
import alone.aa.crawler3.pojo.Job;
import alone.aa.crawler3.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobDao jobDao;

    @Override
    @Transactional //管理事务
    public void save(Job job) {
        //根据url和发布时间查询数据
        Job param = new Job();
        param.setUrl(job.getUrl());
        param.setTime(job.getTime());

        List<Job> list = this.findJob(param);
        if(list.size()==0){
            //如果结果为空，表示招聘信息不存在，或者已经跟新了，则需要更新数据库
            jobDao.save(job);
        }
    }

    @Override
    public List<Job> findJob(Job job) {

        //设置查询条件
        Example example = Example.of(job);
        List list = jobDao.findAll(example);
        return list;
    }
}
