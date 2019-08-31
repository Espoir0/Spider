package alone.aa.crawler3.task;

import alone.aa.crawler3.pojo.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component  //声明Spring管理类的创建和销毁
public class JobProcess implements PageProcessor{

    private String url="https://search.51job.com/list/010000%252C050000%252C160300,000000,0000,00,9,99,java,2,2.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";

    @Override
    public void process(Page page) {
        //String html = page.getHtml().toString();
        //解析页面，获取招聘信息详情的url地址
        List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();

        //判断获取到的List的集合是否为空，为空则为详情页
        if(list.size()==0){
            //如果为空，表示这是招聘的详情页，解析页面，获取详细信息，保存数据
            this.saveJobInfo(page);
        }else {
            //如果不为空，表示这是列表页,解析出详情页面的url地址，放到任务列表中
            for (Selectable selectable : list) {
                String jobInfoUrl = selectable.links().toString();
                //System.out.println(jobInfoUrl);
                //把url地址添加到任务队列中
                page.addTargetRequest(jobInfoUrl);
            }

            //获取下一页的URL
            String bkUrl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
            //System.out.println(bkUrl);
            page.addTargetRequest(bkUrl);
        }
    }

    private void saveJobInfo(Page page) {
        //创建招聘详情对象
        Job job=new Job();
        //解析页面
        Html html = page.getHtml();
        //System.out.println(html);
        job.setCompanyName(html.css("div.cn p.cname a","text").toString());
        job.setCompanyAddr(Jsoup.parse(html.css("div.bmsg").nodes().get(1).toString()).text());
        job.setCompanyInfo(Jsoup.parse(html.css("div.tmsg").toString()).text());
        job.setJobAddr(html.css("div.inbox").nodes().get(1).css("p","text").toString());
        job.setJobInfo(Jsoup.parse(html.css("div.job_msg").toString()).text());
        job.setJobName(html.css("div.cn h1","text").toString());

        String time=Jsoup.parse(html.css("div.cn p.ltype").regex(".*发布").toString()).text();
        job.setTime(time.substring(time.length()-7,time.length()-2));
        job.setUrl(page.getUrl().toString());

        //获取工资
        //Integer[] salary = Salary.getSalary(html.css("div.cn strong", "text").toString());
        //job.setSalaryMax(salary[0]);
        //job.setSalaryMin(salary[1]);
        job.setSalary(html.css("div.cn strong", "text").toString());

        System.out.println(job);
        //保存数据
        page.putField("job",job);
    }

    private Site site=Site.me()
            .setCharset("gbk")
            .setTimeOut(10*1000)
            .setRetrySleepTime(3000) //重试时间
            .setRetryTimes(3); //重试次数
    @Override
    public Site getSite() {
        return site;
    }

    @Autowired
    private JobPipeline jobPipeline;

    //该方法是程序的主方法
    //initialDelay表示任务启动等多久执行方法
    //fixedDelay表示隔多久执行方法
    // @Scheduled(initialDelay = 1000,fixedDelay = 100*1000)
    public void process(){
        Spider.create(new JobProcess())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .addPipeline(this.jobPipeline)
                .run();
    }
}
