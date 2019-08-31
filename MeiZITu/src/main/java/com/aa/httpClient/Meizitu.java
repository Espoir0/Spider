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


import static org.jsoup.Jsoup.*;

public class Meizitu {
    public static void main(String[] args) throws IOException {
        //资源地址
        String baseurl="https://www.mzitu.com/page/";
        for (int i=1;i<20;i++){
            System.out.println("正在下载第"+i+"页");
            //在page后面加参数进行换页
            String url =baseurl+i;
            process(url);
        }
        System.out.println("下载完成");
    }

    //根据传入每页的url解析出图片url然后下载图片
    public static  void process(String url) throws IOException {
        int i=0;
        //获取Document对象
        Document document = connect(url).get();
        //div.indent  表示div的class属性值是indent
        Elements lis = document.select("div.postlist > ul > li");
        for (Element li : lis) {             //遍历所有ul获取

            String imgURL = li.select(" a > img").first().attr("data-original");

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(imgURL);

            //添加头部信息模拟浏览器访问
            //headergroup.updateHeader(new BasicHeader(name, value));
            //List<Header> headers   每个请求头的参数作为一个对象
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpGet.setHeader("User-Agent",Util.getRandomHeader());
            httpGet.setHeader("Referer", "https://www.mzitu.com/");//设置来源

            //3.使用客户端执行请求,获取响应
            CloseableHttpResponse response = httpClient.execute(httpGet);

            //4.获取响应体
            HttpEntity entity = response.getEntity();
            //5.获取响应体的内容
            InputStream is = entity.getContent();


            //获取图片的后缀
            String suffixName = imgURL.substring(imgURL.lastIndexOf("."));
            //创建一个随时间毫秒值变化的的文件名
            String imgName = Util.getDateStr();
            imgName =imgName+suffixName;

            FileOutputStream fos = new FileOutputStream("E://resourse/image/MZT/" + imgName);
            //将输入流中的内容拷贝到输出流
            IOUtils.copy(is,fos);
            //关流
            fos.close();
            is.close();
        }
    }
}

