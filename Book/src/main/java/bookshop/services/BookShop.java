package bookshop.services;

import com.cache.config.InsufficientBalanceException;
import com.cache.config.TransactionNotSupportedException;

public interface BookShop {

  public void purchase(String isbn,String bookName) throws TransactionNotSupportedException, InsufficientBalanceException;

}
