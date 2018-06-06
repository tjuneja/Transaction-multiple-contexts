package bookstock.services;

import com.cache.config.TransactionNotSupportedException;

public interface BookStockService {

    public void updateBookStock(String isbn) throws TransactionNotSupportedException;

}
