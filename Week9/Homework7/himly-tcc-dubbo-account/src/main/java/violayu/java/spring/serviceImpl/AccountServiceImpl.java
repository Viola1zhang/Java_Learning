package violayu.java.spring.serviceImpl;

import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import violayu.java.spring.domain.Account;
import violayu.java.spring.mapper.AccountMapper;
import violayu.java.spring.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Override
	@HmilyTCC(confirmMethod ="confirm", cancelMethod="cancel")
	public boolean pay(Account account) {
		// TODO Auto-generated method stub
		boolean isSuccess = accountMapper.payment(account);
		
		return isSuccess;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean confirm(Account account) {
		System.out.println("------dubbo tcc excute confirm payment interface-----");
		System.out.println("Confirm Account is "+ account.toString());
		return true;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean cancel(Account account) {
		System.out.println("------dubbo tcc excute cancel payment interface-----");
		System.out.println("Cancel Account is "+ account.toString());
		return true;
	}

}
