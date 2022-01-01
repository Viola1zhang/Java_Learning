package violayu.java.spring.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class RpcfxEncoder extends MessageToByteEncoder<RpcfxProtocol> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RpcfxProtocol msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("Netty rpc encode run");
		 out.writeInt(msg.getLength());
		 out.writeBytes(msg.getContent());
	}

}
