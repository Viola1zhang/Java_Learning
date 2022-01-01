package violayu.java.spring.netty.server;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.context.ApplicationContext;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import violayu.java.spring.api.RpcfxRequest;
import violayu.java.spring.api.RpcfxResponse;
import violayu.java.spring.netty.common.RpcfxProtocol;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class RpcfxServerHandler extends SimpleChannelInboundHandler<RpcfxProtocol> {
	
	private ApplicationContext applicationContext;
	
	
	public RpcfxServerHandler(ApplicationContext applicationContext) {
		// TODO Auto-generated constructor stub
		this.applicationContext =applicationContext;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcfxProtocol msg) throws Exception {
		// TODO Auto-generated method stub
		RpcfxRequest rpcfxRequest = JSON.parseObject(new String(msg.getContent(),CharsetUtil.UTF_8),RpcfxRequest.class);
		
		
		RpcfxResponse response = invoke(rpcfxRequest);
		
		RpcfxProtocol message = new RpcfxProtocol();
		String requestJson = JSON.toJSONString(response);
		message.setLength(requestJson.getBytes(CharsetUtil.UTF_8).length);
		message.setContent(requestJson.getBytes(CharsetUtil.UTF_8));
		
		ctx.writeAndFlush(message).sync();
		
		
	}
	
	private RpcfxResponse invoke(RpcfxRequest request) {
		RpcfxResponse response = new RpcfxResponse();
		String serviceClass = request.getServiceClass();
		
		Object service = applicationContext.getBean(serviceClass);
		Method method = resolveMethodFromClass(service.getClass(),request.getMethod());
		Object result;
		try {
			result = method.invoke(service, request.getParams());
			response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
			response.setStatus(true);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return response;
		
	}
	
	private Method resolveMethodFromClass(Class<?> klass, String methodName) {
		return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
	}

}
