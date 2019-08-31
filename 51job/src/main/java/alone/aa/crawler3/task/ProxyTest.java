package alone.aa.crawler3.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

@Component
public class ProxyTest implements PageProcessor {

    @Scheduled(fixedDelay = 1000)
    public void process(){
        //创建下载器downloader
        HttpClientDownloader downloader = new HttpClientDownloader();
        //给下载器设置代理服务器信息
        downloader.setProxyProvider(SimpleProxyProvider.from(new us.codecraft.webmagic.proxy.Proxy("117.127.16.208",8080)));

        Spider.create(new ProxyTest())
                .addUrl("http://200019.ip138.com/")
                .setDownloader(downloader)  //设置下载器
                .run();

    }

    @Override
    public void process(Page page) {
        System.out.println(page.getHtml().css("body p","text").toString());
    }

    private Site site=Site.me();
    @Override
    public Site getSite() {
        return site;
    }
}
