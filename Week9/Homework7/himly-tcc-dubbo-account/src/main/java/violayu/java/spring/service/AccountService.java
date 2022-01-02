package violayu.java.spring.service;

import org.dromara.hmily.annotation.Hmily;

import violayu.java.spring.domain.Account;

public interface AccountService {
	@Hmily
	boolean pay(Account account);

}
