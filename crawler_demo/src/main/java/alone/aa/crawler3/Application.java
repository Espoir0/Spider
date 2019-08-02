package alone.aa.crawler3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //设置定时任务
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
