package bookstock.services;

import com.cache.config.TransactionNotSupportedException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Transactional(rollbackFor = TransactionNotSupportedException.class)
public class BookStockServiceImpl  implements BookStockService {

//    @Autowired
//    TransactionTemplate transactionTemplateBean;
//
//    @Autowired
//    DataSourceTransactionManager transactionManager;
//
//    @Autowired
//    DataSource dataSource;
private static final Logger log = Logger.getLogger(BookStockServiceImpl.class);
    @Autowired
    JdbcTemplate template;


    @CachePut(value="bookStock", key="#isbn")
    @Override
    public void updateBookStock(String isbn) throws TransactionNotSupportedException {

        int stock  = template.queryForObject("SELECT STOCK FROM BOOK_STOCK WHERE ISBN =?"
                        ,Integer.class,isbn);
                template.update("UPDATE BOOK_STOCK SET STOCK = STOCK - 1 WHERE ISBN = ?", isbn);
               int stock2=template.queryForObject("SELECT STOCK FROM BOOK_STOCK WHERE ISBN =?"
                ,Integer.class,isbn);

               if(stock == stock2){
                   throw new TransactionNotSupportedException("Stock Mismatch");
               }

    }
}
