package com.aa.httpClient;

import com.aa.wallpaper.Util;
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

import static org.jsoup.Jsoup.connect;

public class ZhiHu {
    public static void main(String[] args) throws IOException {
        String url="http://www.417mm.com/list/index57.html";
        pasre(url);

    }

    private static void pasre(String url) throws IOException {
        System.out.println("分类页面->"+url);

        Document document = connect(url).get();
        Element ul = document.getElementsByTag("ul").get(1);
        Elements lis = ul.select("li");

        int i=0;
        for (Element li : lis) {
            String href = li.select("div").first().select("a").first().attr("href");

            if (i<8 ){
                i++;
                continue;
            }
            href = "http://www.417mm.com"+href;
            process1(href);
        }

    }

    private static void process1(String href) throws IOException {
        System.out.println("页面->"+href);
        //获取Document对象
        Document document = connect(href).get();

        String href2 = document.select("div.l").select("a").attr("href");
        href2 = "http://www.417mm.com"+href2;


        process(href2);
    }

    //根据传入每页的url解析出资源的url
    public static  void process(String url) throws IOException {
        String path="E:\\resourse\\movie";

        System.out.println("页面->"+url);
        //获取Document对象
        Document document = connect(url).get();

       //获取标题
        String title = document.getElementsByTag("h2").first().text();


        //先获取所有得script标签，.eq(6)表示获取第六个标签
        Elements script = document.getElementsByTag("script").eq(6);
        String scriptStr = script.toString();             //转化为字符串
        int startIndex = scriptStr.indexOf("video=[");    //找到video,所在得字符串
        String videoUrl = scriptStr.substring(startIndex);

        int start=videoUrl.indexOf("https://");
        int endIndex=videoUrl.indexOf(".mp4");
        String resourceUrl = videoUrl.substring(start, endIndex)+".mp4";
        downloadResource(title,resourceUrl,path);

    }

    private static void downloadResource(String title,String resourceUrl, String savePath) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(resourceUrl);

        //添加头部信息模拟浏览器访问
        //headergroup.updateHeader(new BasicHeader(name, value));
        //List<Header> headers   每个请求头的参数作为一个对象
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("User-Agent",Util.getRandomHeader());
        httpGet.setHeader("Referer", "http://www.417mm.com/");//设置来源

        //3.使用客户端执行请求,获取响应
        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode"+statusCode+"开始下载");
        System.out.println("resourceUrl -> "+resourceUrl);

        //4.获取响应体
        HttpEntity entity = response.getEntity();
        //5.获取响应体的内容
        InputStream is = entity.getContent();
        //获取资源的后缀
        String suffixName = resourceUrl.substring(resourceUrl.lastIndexOf("."));
        //创建一个随时间毫秒值变化的的文件名
        String imgName = Util.getDateStr();
        imgName =imgName+suffixName;

        FileOutputStream fos = new FileOutputStream(savePath+"\\" + imgName);
        entity.writeTo(fos);

        IOUtils.copy(is,fos);           //将输入流中的内容拷贝到输出流
        //关流
        fos.close();
        is.close();
        System.out.println("下载完成");
    }
}

