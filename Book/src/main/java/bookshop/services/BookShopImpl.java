package bookshop.services;

import account.services.AccountService;
import bookstock.services.BookStockService;
import com.cache.config.InsufficientBalanceException;
import com.cache.config.TransactionNotSupportedException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;

@Controller
public class BookShopImpl  implements BookShop {

    private static final Logger log = Logger.getLogger(BookShopImpl.class);


//    @Autowired
//    TransactionTemplate transactionTemplateBean;
//
//    @Autowired
//    DataSourceTransactionManager transactionManager;


    @Autowired
    BookShopManager bookShopManager;




   @ResponseBody
   @GetMapping("/{isbn}/{bookName}")
   public String purchaseBook(@PathVariable String isbn, @PathVariable String bookName){
       try{
           purchase(isbn,bookName);
           return "Purchase succesful. Happy Reading !";
       }catch (Exception e){
           log.trace("Purchase Not successful : ",e);
           return "You ain't got no money!";
       }

   }



    @Override
    public void purchase( String isbn,  String userName) throws TransactionNotSupportedException, InsufficientBalanceException {

       bookShopManager.purchase(isbn,userName);
    }
}
