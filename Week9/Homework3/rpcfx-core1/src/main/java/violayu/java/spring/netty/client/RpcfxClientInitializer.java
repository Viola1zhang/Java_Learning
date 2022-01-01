package violayu.java.spring.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import violayu.java.spring.netty.common.RpcfxDecoder;
import violayu.java.spring.netty.common.RpcfxEncoder;

public class RpcfxClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("Message Encoder", new RpcfxEncoder());
		pipeline.addLast("Message Decoder", new RpcfxDecoder());
		pipeline.addLast("clientHandler", new RpcfxClientSyncHandler());
	}

}
