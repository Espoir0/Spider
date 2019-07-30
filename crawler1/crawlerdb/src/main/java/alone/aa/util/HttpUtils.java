package alone.aa.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class HttpUtils {
    private static int i=0;
    private PoolingHttpClientConnectionManager cm;
    public HttpUtils(){
        //连接池管理器，设置最大连接数
        cm=new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(10);
    }


    //获取页面数据
    public String doGetHtml(String url){
        //获取client对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
        //get请求
        HttpGet get = new HttpGet(url);

        get.setConfig(getConfig());

        CloseableHttpResponse response = null;
        try {
            //发起请求，得到响应
            response = client.execute(get);
            if(response.getStatusLine().getStatusCode()==200){{
                if(response!=null){
                    //获取响应内容
                    String content = EntityUtils.toString(response.getEntity());
                    return content;
                }
            }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       return "";
    }

    //获取图片
    public String doGetImage(String url){
        i++;
        //获取client对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
        //get请求
        HttpGet get = new HttpGet(url);

        get.setConfig(getConfig());

        CloseableHttpResponse response = null;
        try {
            //发起请求，得到响应
            response = client.execute(get);
            if(response.getStatusLine().getStatusCode()==200){{
                if(response!=null){
                    //获取后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //图片名
                    String imgName=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+"_"+i+extName;
                    //String imgName = UUID.randomUUID().toString() + extName;可以使用UUID作为名字，不重复
                    //下载
                    FileOutputStream fos = new FileOutputStream(new File("E://img/" + imgName));
                    response.getEntity().writeTo(fos);
                    //返回图片名
                    return imgName;
                }
            }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    //获取get请求的配置信息
    private RequestConfig getConfig(){
        RequestConfig config=RequestConfig.custom()
                .setConnectTimeout(5000)  //建立连接的最大时间
                .setConnectionRequestTimeout(5000) //请求时间
                .setSocketTimeout(50000)  //数据传输的时间
                .build();
        return config;
    }
}
