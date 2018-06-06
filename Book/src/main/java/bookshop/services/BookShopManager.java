package bookshop.services;

import account.services.AccountService;
import bookstock.services.BookStockService;
import com.cache.config.InsufficientBalanceException;
import com.cache.config.TransactionNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookShopManager {
    @Autowired
    JdbcTemplate template;

    @Autowired
    BookStockService bookStockService;

    @Autowired
    AccountService accountService;

    @CachePut(value = "purchase",key="#isbn")
    @Transactional(rollbackFor = InsufficientBalanceException.class)
    public void purchase(final String isbn, final String userName)
            throws TransactionNotSupportedException, InsufficientBalanceException {

        int price = template.queryForObject("SELECT PRICE FROM BOOK WHERE ISBN =?"
                ,Integer.class,isbn);
        bookStockService.updateBookStock(isbn);
        accountService.accountUpdate(price,userName);

    }
}
