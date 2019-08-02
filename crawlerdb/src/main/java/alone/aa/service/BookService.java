package alone.aa.service;

import alone.aa.pojo.Book;

import java.util.List;

public interface BookService {
    //添加
    void save(Book book);
    //根据条件查询
    List<Book> findAll(Book book);
}
