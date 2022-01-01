package violayu.java.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import violayu.java.spring.api.RpcfxRequest;
import violayu.java.spring.api.RpcfxResponse;
import violayu.java.spring.netty.client.RpcfxNettyClientSync;

public class RpcfxInvocationHandler implements InvocationHandler, MethodInterceptor {
	
	private final Class<?> serviceClass;
	private final String url;
	<T> RpcfxInvocationHandler(Class<T> serviceClass, String url) {
		// TODO Auto-generated constructor stub
		this.serviceClass = serviceClass;
		this.url =url;
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		// TODO Auto-generated method stub
		
		return process(serviceClass, method, args, url);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		return process(serviceClass, method,args, url);
	}
	
	private Object process(Class<?> service, Method method, Object[] params, String url) {
		RpcfxRequest rpcfxRequest = new RpcfxRequest();
		rpcfxRequest.setServiceClass(service.getName());
		rpcfxRequest.setMethod(method.getName());
		rpcfxRequest.setParams(params);
		
		RpcfxResponse rpcfxResponse;
		rpcfxResponse = RpcfxNettyClientSync.getInstance().getResponse(rpcfxRequest, url);
		
		assert rpcfxResponse !=null;
		if(!rpcfxResponse.getStatus()) {
			rpcfxResponse.getExcption().printStackTrace();
			return null;
		}
		
		return JSON.parse(rpcfxResponse.getResult().toString());
		
	}

}
