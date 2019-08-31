package alone.aa.service.impl;

import alone.aa.dao.BookDao;
import alone.aa.pojo.Book;
import alone.aa.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Transactional  //开启事务提交事务
    @Override
    public void save(Book book) {
        //bookDao.save(book);
    }

    @Override
    public List<Book> findAll(Book book) {
        //声明查询条件
        Example<Book> example = Example.of(book);
        //根据条件进行查询
        List<Book> list = bookDao.findAll(example);
        return list;
    }
}
