package violayu.java.spring.netty.client;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import violayu.java.spring.api.RpcfxRequest;
import violayu.java.spring.api.RpcfxResponse;
import violayu.java.spring.netty.common.RpcfxProtocol;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class RpcfxNettyClientSync {
	private enum EnumSingleton {
		INSTANCE;
		private RpcfxNettyClientSync instance;
		EnumSingleton(){
			instance = new RpcfxNettyClientSync();
		}
		
		public RpcfxNettyClientSync getSingleton() {
			return instance;
		}
	}
	
	public static RpcfxNettyClientSync getInstance() {
		return EnumSingleton.INSTANCE.getSingleton();
	}
	
	private ConcurrentHashMap<String, Channel> channelPool = new ConcurrentHashMap<>();
	private EventLoopGroup clientGroup = new NioEventLoopGroup (new ThreadFactoryBuilder().setNameFormat("client work-%d").build());
	
	private RpcfxNettyClientSync() {
		
	}
	
	public RpcfxResponse getResponse(RpcfxRequest rpcfxRequest, String url) {
		RpcfxProtocol request = convertNettyRequest(rpcfxRequest);
		URI uri = new URI(url);
		String cacheKey = uri.getHost() +":"+uri.getPort();
		
		if(channelPool.containsKey(cacheKey)) {
			Channel channel = channelPool.get(cacheKey);
			if(!channel.isActive() || !channel.isWritable() ||!channel.isOpen()) {
				System.out.println("Channal can not reuse");
			}else {
				try {
					RpcfxClientSyncHandler handler = new RpcfxClientSyncHandler();
					handler.setLatch(new CountDownLatch(1));
					channel.pipeline().replace("clientHandler", "clientHandler", handler);
					channel.writeAndFlush(request).sync();
					return handler.getResponse();
				}catch(Exception e) {
					channel.close();
					channelPool.remove(cacheKey);
					
				}
				
				
			}
		}
		
		RpcfxClientSyncHandler handler = new RpcfxClientSyncHandler();
		handler.setLatch(new CountDownLatch(1));
		Channel channel = createChannel(uri.getHost(), uri.getPort());
		channelPool.put(cacheKey, channel);
		
		channel.writeAndFlush(request).sync();
		return handler.getResponse();
	}
	
	private Channel createChannel(String address, int port) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(clientGroup)
		.option(ChannelOption.SO_REUSEADDR, true)
		.option(ChannelOption.TCP_NODELAY, true)
		.option(ChannelOption.AUTO_CLOSE, true)
		.option(ChannelOption.SO_KEEPALIVE, true)
		.channel(NioSocketChannel.class)
		.handler(new RpcfxClientInitializer());
		
		return bootstrap.connect(address,port).sync().channel();
	}
	
	private RpcfxProtocol convertNettyRequest(RpcfxRequest rpcfxRequest) {
		RpcfxProtocol request = new RpcfxProtocol();
		String requestJson = JSON.toJSONString(rpcfxRequest);
		request.setLength(requestJson.getBytes(CharsetUtil.UTF_8).length);
		request.setContent(requestJson.getBytes(CharsetUtil.UTF_8));
		return request;
	}
	
	public void destroy() {
		clientGroup.shutdownGracefully();
	}
	
}
