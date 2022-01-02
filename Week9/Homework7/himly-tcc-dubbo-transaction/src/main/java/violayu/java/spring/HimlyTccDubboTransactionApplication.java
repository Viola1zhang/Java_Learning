package violayu.java.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import violayu.java.spring.service.TransactionService;

@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
public class HimlyTccDubboTransactionApplication implements ApplicationRunner{
	
	@Autowired
	private TransactionService transactionService;
	public static void main(String[] args) {
		SpringApplication.run(HimlyTccDubboTransactionApplication.class, args);
	}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		transactionService.transaction();
	}
	
	

}
