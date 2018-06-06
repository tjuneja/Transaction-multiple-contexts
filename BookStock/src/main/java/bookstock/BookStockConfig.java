package bookstock;

import Spring.Exported;
import Spring.ExportedConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import bookstock.services.BookStockService;
import bookstock.services.BookStockServiceImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@Import(ExportedConfig.class)
@EnableTransactionManagement
@EnableCaching
public class BookStockConfig {



    @Exported
    @Bean
    public BookStockService bookStockService(){

        return new BookStockServiceImpl();
    }


}
