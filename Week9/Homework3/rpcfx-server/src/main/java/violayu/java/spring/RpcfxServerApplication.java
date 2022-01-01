package violayu.java.spring;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import violayu.java.spring.netty.server.RpcfxNettyServer;

@SpringBootApplication
public class RpcfxServerApplication implements ApplicationRunner{

	
	  
	 
	private final RpcfxNettyServer rpcfxNettyServer;
	
	public RpcfxServerApplication(RpcfxNettyServer rpcfxNettyServer) {
		this.rpcfxNettyServer = rpcfxNettyServer;
	}
	
	public static void main(String[] args) {
		  SpringApplication.run(RpcfxServerApplication.class, args); 
	  }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		try {
			rpcfxNettyServer.run();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			rpcfxNettyServer.destroy();
		}
		
	}
	

}
