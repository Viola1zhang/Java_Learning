package violayu.java.spring.proxy;

import net.sf.cglib.proxy.Enhancer;

public class RpcfxClientCglib extends RpcfxProxy implements RpcfxClient {

	@Override
	public <T> T create(Class<T> serviceClass, String url) {
		// TODO Auto-generated method stub
		if(!isExit(serviceClass.getName())) {
			add(serviceClass.getName(), newProxy(serviceClass, url));
		}
		return (T) getProxy(serviceClass.getName());
	}
	
	private <T> T newProxy(Class<T> serviceClass, String url) {
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(new RpcfxInvocationHandler(serviceClass,url));
		enhancer.setSuperclass(serviceClass);
		return (T) enhancer.create();
	}

}
