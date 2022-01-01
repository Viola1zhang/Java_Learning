package violayu.java.spring;



import violayu.java.spring.domain.Order;
import violayu.java.spring.domain.User;
import violayu.java.spring.proxy.RpcfxByteBuddy;
import violayu.java.spring.proxy.RpcfxClient;
import violayu.java.spring.proxy.RpcfxClientJdk;
import violayu.java.spring.service.OrderService;
import violayu.java.spring.service.UserService;

public class ClientApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RpcfxClient jdk = new RpcfxClientJdk();
		UserService userService = jdk.create(UserService.class, "http://localhost:8080/");
		User user = userService.findById(1);
		if(user == null) {
			System.out.println("Service invoke error");
			return;
		}
		
		RpcfxClient buddy = new RpcfxByteBuddy();
		OrderService orderService = buddy.create(OrderService.class,"http://localhost:8080/");
		
		Order order = orderService.findById(1);
		if(order == null) {
			System.out.println("Service invoke error");
			return;
		}

	}

}
