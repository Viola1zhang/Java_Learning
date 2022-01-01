package violayu.java.spring.proxy;

import java.lang.reflect.Proxy;

public class RpcfxClientJdk extends RpcfxProxy implements RpcfxClient {

	@Override
	public <T> T create(Class<T> serviceClass, String url) {
		// TODO Auto-generated method stub
		if(!isExit(serviceClass.getName())) {
			add(serviceClass.getName(), newProxy(serviceClass, url));
		}
		return (T) getProxy(serviceClass.getName());
	}
	
	private<T> T newProxy(Class<T> serviceClass, String url){
		ClassLoader loader = RpcfxClientJdk.class.getClassLoader();
		Class[] classes = new Class[] {serviceClass};
		return (T) Proxy.newProxyInstance(loader, classes, new RpcfxInvocationHandler(serviceClass, url));
	}

}
