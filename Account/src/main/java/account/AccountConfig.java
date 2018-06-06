package account;


import Spring.Exported;
import Spring.ExportedConfig;
import account.services.AccountService;
import account.services.AccountServiceImpl;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@Import(ExportedConfig.class)
@EnableTransactionManagement
@EnableCaching
public class AccountConfig {

    @Bean
    @Exported
    public AccountService accountService(){
        return new AccountServiceImpl();
    }


}
