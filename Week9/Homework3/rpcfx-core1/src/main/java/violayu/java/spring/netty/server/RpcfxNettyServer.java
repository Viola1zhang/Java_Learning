package violayu.java.spring.netty.server;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import violayu.java.spring.netty.common.RpcfxDecoder;
import violayu.java.spring.netty.common.RpcfxEncoder;

@Component
public class RpcfxNettyServer {
	private final ApplicationContext context;
	private EventLoopGroup boss;
	private EventLoopGroup worker;
	
	public RpcfxNettyServer(ApplicationContext context) {
		this.context = context;
	}
	
	public void destroy() {
		worker.shutdownGracefully();
		boss.shutdownGracefully();
	}
	
	public void run() {
		boss = new NioEventLoopGroup(1);
		worker = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boss,worker)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer() {

						@Override
						protected void initChannel(Channel channel) throws Exception {
							// TODO Auto-generated method stub
							ChannelPipeline pipeline = channel.pipeline();
							pipeline.addLast("Message Encoder", new RpcfxEncoder());
							pipeline.addLast("Message Decoder", new RpcfxDecoder());
	                        pipeline.addLast("Message Handler", new RpcfxServerHandler(context));
							
							
						}
						
					});
		
		int port =8080;
		try {
			Channel channel = serverBootstrap.bind(port).sync().channel();
			System.out.println("Netty server listen in port: " + port);
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
