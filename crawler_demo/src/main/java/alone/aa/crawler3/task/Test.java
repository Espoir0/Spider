package alone.aa.crawler3.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Test {
    //使用cron表达式设置定时任务 每隔五秒执行一次  0/5 * * * * *
    @Scheduled(cron="0/5 * * * * * ")
    public void test(){
        System.out.println("我是定时器");
    }
}
