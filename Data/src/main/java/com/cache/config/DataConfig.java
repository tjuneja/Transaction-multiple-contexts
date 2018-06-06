    package com.cache.config;

    import Spring.Exported;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.datasource.DataSourceTransactionManager;
    import org.springframework.jdbc.datasource.DriverManagerDataSource;
    import org.springframework.transaction.support.TransactionTemplate;

    import javax.sql.DataSource;
    @Configuration
    public class DataConfig{

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
            dataSource.setUrl("jdbc:mysql://localhost:3306/poc");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            return dataSource;
        }


        @Bean
        public DataSourceTransactionManager transactionManager() {
            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
            transactionManager.setDataSource(dataSource());
            return transactionManager;
        }



        @Bean
        public TransactionTemplate transactionTemplate() {
            TransactionTemplate transactionTemplate = new TransactionTemplate();
            transactionTemplate.setTransactionManager(transactionManager());
            return transactionTemplate;
        }


        @Bean
        public JdbcTemplate jdbcTemplate(){
            JdbcTemplate template =new JdbcTemplate(dataSource());
            return template;
        }

        //@Exported
        @Bean
        public TransactionNotSupportedException exception(){
            return new TransactionNotSupportedException();
        }



        @Bean
        public InsufficientBalanceException balanceException(){
            return new InsufficientBalanceException();
        }
    }





