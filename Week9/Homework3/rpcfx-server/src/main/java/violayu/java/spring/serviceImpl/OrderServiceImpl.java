package violayu.java.spring.serviceImpl;

import violayu.java.spring.domain.Order;
import violayu.java.spring.exception.CustomException;
import violayu.java.spring.service.OrderService;

public class OrderServiceImpl implements OrderService {

	@Override
	public Order findById(int id) {
		// TODO Auto-generated method stub
		return new Order(1,"RPC",1);
	}

	@Override
	public Order findError() {
		// TODO Auto-generated method stub
		throw new CustomException("Custom Exception");
	}

}
