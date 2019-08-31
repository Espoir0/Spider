package com.aa.wallpaper;

import com.typesafe.config.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.jsoup.Jsoup.connect;

@Data
@Slf4j
public class Wallpaper {
    private static List<String> resourceUrlsList;
    private static int size;

    public static void main(String[] args){
        Config config = AppConfig.getInstance();
        String url = config.getString("config.url");
        String savePath = config.getString("config.savePath");

        resourceUrlsList = parse(url);
        size=resourceUrlsList.size();

        Runnable run=()->{
            while (size>0){
                String resourceUrl = resourceUrlsList.get(--size);
                downloadResource(resourceUrl,savePath);
            }
        };

        new Thread(run).start();
        new Thread(run).start();
        new Thread(run).start();
        new Thread(run).start();
        new Thread(run).start();
    }

    //根据传入每页的url解析出资源的url
    private static List<String> parse(String url) {

        //获取Document对象
        Document document = null;
        try {
            document = connect(url).get();
            log.info("初始页面 -> url -> " + url);
            String title = document.getElementsByTag("title").first().text();
            log.info("页面标题 -> "+title);

            Elements figures = document.getElementsByTag("figure");

            List<String> list = new ArrayList<String>();

            for (Element figure : figures) {
                String img = figure.select("img").attr("src");
                list.add(img);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void downloadResource(String resourceUrl, String savePath){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(resourceUrl);

        //添加头部信息模拟浏览器访问
        //headergroup.updateHeader(new BasicHeader(name, value));
        //List<Header> headers   每个请求头的参数作为一个对象
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("User-Agent", Util.getRandomHeader());
        httpGet.setHeader("Referer", "http://www.baidu.com/");//设置来源

        //3.使用客户端执行请求,获取响应
        try {
            CloseableHttpResponse response = null;
            response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                log.info("downloading...  resourceUrl -> " + resourceUrl+"  ");

                //4.获取响应体
                HttpEntity entity = response.getEntity();
                //5.获取响应体的内容
                InputStream is = entity.getContent();
                //获取资源的后缀
                String suffixName = resourceUrl.substring(resourceUrl.lastIndexOf("."));
                //创建一个随时间毫秒值变化的的文件名
                String imgName = Util.getDateStr();
                imgName = imgName + suffixName;

                FileOutputStream fos = new FileOutputStream(savePath + "\\" + imgName);

                IOUtils.copy(is, fos);           //将输入流中的内容拷贝到输出流
                //关流
                fos.close();
                is.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}