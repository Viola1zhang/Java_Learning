package violayu.java.spring.serviceImpl;

import violayu.java.spring.domain.User;
import violayu.java.spring.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return new User(id,"RPC");
	}

}
