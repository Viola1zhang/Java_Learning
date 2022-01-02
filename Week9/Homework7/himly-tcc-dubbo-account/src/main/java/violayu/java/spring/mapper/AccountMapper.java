package violayu.java.spring.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import violayu.java.spring.domain.Account;

public interface AccountMapper {
	 @Update("update `himly_dubbo_account` set us_account = us_account + #{us_account}, cny_account = cny_account + " +
	            "#{cny_account} where us_account >= #{us_account} and cny_account >= #{cny_account} and id = #{id}")
	    boolean payment(Account account);

	    
	    @Select("select * from himly_dubbo_account where id = #{id}")
	    Account queryOne(Account account);

}
