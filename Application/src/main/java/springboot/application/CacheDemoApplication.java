package springboot.application;

import account.AccountConfig;
import bookshop.BookConfig;
import bookstock.BookStockConfig;
import com.cache.config.DataConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;


@SpringBootConfiguration
@ComponentScan("com.cache.config")
public class CacheDemoApplication {
    public static void main(String[] args) {


        new SpringApplicationBuilder()
                .sources(CacheDemoApplication.class,DataConfig.class).web(WebApplicationType.NONE)
                .child(BookStockConfig.class).web(WebApplicationType.NONE)
                .sibling(AccountConfig.class).web(WebApplicationType.NONE)
                .sibling(BookConfig.class).web(WebApplicationType.SERVLET)
                .run(args);
    }
}
