package account.services;

import com.cache.config.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

@Transactional
public class AccountServiceImpl implements AccountService {
//
//    @Autowired
//    TransactionTemplate transactionTemplateBean;
//
//    @Autowired
//    DataSourceTransactionManager transactionManager;
//
//    @Autowired
//    DataSource dataSource;

    @Autowired
    JdbcTemplate template;


    @CachePut(value="account",key = "#price")
    @CacheEvict(value = "account",condition = "#price<10",beforeInvocation=false)
    @Override
    public void accountUpdate(int price, String userName) throws InsufficientBalanceException {

                template.update("UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE USERNAME = ?",
                        price, userName);
                int balance = template.queryForObject("SELECT BALANCE FROM ACCOUNT WHERE USERNAME =?",
                        Integer.class, userName);
        if (balance < 0) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }

    }
}
