package account.services;

import com.cache.config.InsufficientBalanceException;

public interface AccountService {

   public void accountUpdate(int price,String userName) throws InsufficientBalanceException;
}
