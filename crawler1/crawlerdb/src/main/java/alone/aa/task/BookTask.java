package alone.aa.task;

import alone.aa.pojo.Book;
import alone.aa.service.BookService;
import alone.aa.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BookTask {
    @Autowired
    private BookService bookService;
    @Autowired
    private HttpUtils utils;
    //当前任务完成之后多长时间进行下一步操作
    @Scheduled(fixedDelay = 100*1000)
    public void task(){
        //声明需要解析的地址
        String url="https://book.douban.com/top250?start=";
        //按照页面对书籍遍历
        for (int i = 0; i < 10; i++) {
            String html = utils.doGetHtml(url + 25 * i);

            //解析页面，获取商品数据
            this.parse(html);
        }
        System.out.println("抓取完成");

    }

    //解析页面，获取商品数据
    private void parse(String html) {
        //解析html获取Document
        Document document = Jsoup.parse(html);

        //div.indent  表示div的class属性值是indent
        Elements trs = document.select("div.indent > table > tbody > tr");
        for (Element tr : trs) {
            Book book = new Book();
            //https://img3.doubanio.com/view/subject/m/public/s1727290.jpg
            //书对应的封面名称
            String imgUrl = tr.select("td > a > img").first().attr("src");
            String imgName = utils.doGetImage(imgUrl);
            book.setImgName(imgName);

            //获取书名
            String bookName = tr.select("td > div > a").first().attr("title");
            book.setBookName(bookName);

            //获取价格等描述信息
            String desc = tr.select("td > p.pl").first().html();
            book.setDesc(desc);

            //评分
            String score = tr.select("span.rating_nums").html();
            book.setScore(score);

            //评价人数
            String person = tr.select("span.pl").html();
            book.setPerson(person);

            //
            String hot = tr.select("span.inq").html();
            book.setHot(hot);

            bookService.save(book);

            //记得进行部分测试
        }

    }
}
