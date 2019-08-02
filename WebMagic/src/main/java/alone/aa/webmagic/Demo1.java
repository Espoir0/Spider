package alone.aa.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

public class Demo1 implements PageProcessor {
    //解析页面x
    public void process(Page page) {
/*
        //解析返回的数据page，并把解析到的结果放到ResultItems中
        //使用css获取，参数可以设置属性
        page.putField("div",page.getHtml().css("div.screening-bd ul li","data-title").all());

        //使用xpath获取 w3s使用教程
        page.putField("div2",page.getHtml().xpath("//div[@class=screening-bd]/ul/li/ul/li/a"));

        //使用正则表达式
        page.putField("div3",page.getHtml().css("ul.ui-slide-content li").regex(".*歌唱.*").all());
        //get和toString()方法相同，获取单个结果
        //处理结果API
        page.putField("div3",page.getHtml().css("ul.ui-slide-content li").regex(".*歌唱.*").get());
        page.putField("div3",page.getHtml().css("ul.ui-slide-content li").regex(".*歌唱.*").toString());

        */

        //获取链接
        System.out.println(".....................");
        page.addTargetRequests(page.getHtml().css("div.indent").links().regex(".*1/$").all());
        //#content > div > div.article > div > div > table:nth-child(2) > tbody > tr > td:nth-child(2) > div > a
        page.putField("div6",page.getHtml().css("div#content h1").all());
    }
    private Site site=Site.me()
            .setCharset("utf-8")
            .setTimeOut(10000)//超时
            .setRetryTimes(3000)//重新请求的时间
            .setSleepTime(3) //重试次数
            ;

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new Demo1())
                .addUrl("https://movie.douban.com/chart")//设置爬取数据的页面
                .addPipeline(new FilePipeline("E:/crawler")) //将爬取到的结果存入文件，默认是输出到控制台
                .thread(5)//设置多线程
                //设置存储方式和去重方式内存存储，使用Bloom去重
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)));
        //查看去重方式
        Scheduler scheduler = spider.getScheduler();
        spider.run();//执行
    }
}
