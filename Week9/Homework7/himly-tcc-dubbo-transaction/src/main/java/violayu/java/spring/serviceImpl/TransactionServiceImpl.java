package violayu.java.spring.serviceImpl;

import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import violayu.java.spring.domain.Account;
import violayu.java.spring.service.AccountService;
import violayu.java.spring.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	final private AccountService accountService;
	
	@Autowired(required=false)
	public TransactionServiceImpl(AccountService accountService) {
		this.accountService = accountService;
	}
	@Override
	@HmilyTCC(confirmMethod="confirmOrderStatus", cancelMethod="cancelOrderStatus")
	public void transaction() {
		// TODO Auto-generated method stub
		
		transactionA();
		transactionB();

	}
	
	private void transactionA() {
		// TODO Auto-generated method stub
		Account account = new Account();
		account.setId(1L);
		account.setUs_account(-1L);
		account.setCny_account(7L);
		accountService.pay(account);
		
	}
	
	private void transactionB() {
		// TODO Auto-generated method stub
		Account account = new Account();
		account.setId(2L);
		account.setUs_account(1L);
		account.setCny_account(-7L);
		accountService.pay(account);
		
	}
	
	public void confirmOrderStatus() {
        System.out.println("=========Confirm status complete================");
    }

    public void cancelOrderStatus() {
    	System.out.println("=========Cancel status complete================");
    }

}
