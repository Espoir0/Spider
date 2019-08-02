package alone.aa.crawler3.task;

import alone.aa.crawler3.pojo.Job;
import alone.aa.crawler3.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component  //标识由Spring可以注入对象
public class JobPipeline implements Pipeline {
    @Autowired
    private JobService jobService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取封装好的对象
        Job job = resultItems.get("job");
        if(job!=null){
            this.jobService.save(job);
        }
    }
}
