package violayu.java.spring.service;

import violayu.java.spring.domain.Order;

public interface OrderService {
	Order findById(int id);
	Order findError();

}
