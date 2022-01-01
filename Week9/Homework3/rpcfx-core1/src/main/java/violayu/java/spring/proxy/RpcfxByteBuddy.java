package violayu.java.spring.proxy;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

public class RpcfxByteBuddy extends RpcfxProxy implements RpcfxClient {

	@Override
	public <T> T create(Class<T> serviceClass, String url) {
		// TODO Auto-generated method stub
		if(!isExit(serviceClass.getName())) {
			add(serviceClass.getName(), newProxy(serviceClass, url))
		}
		return (T) getProxy(serviceClass.getName());
	}
	
	@SneakyThrows
	private <T> T newProxy(Class<T> serviceClass, String url) {
		return (T) new ByteBuddy().subclass(Object.class)
				.implement(serviceClass)
				.intercept(InvocationHandlerAdapter.of(new RcpfxInvocationHandler(serviceClass, url)))
				.make()
				.load(RpcfxByteBuddy.class.getClassLoader())
				.getLoaded()
				.getDeclaredConstrutor()
				.newInstance();
	}

}
