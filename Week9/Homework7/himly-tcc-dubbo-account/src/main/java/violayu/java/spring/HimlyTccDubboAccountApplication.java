package violayu.java.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
public class HimlyTccDubboAccountApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(HimlyTccDubboAccountApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		springApplication.run(args);
	}

}
