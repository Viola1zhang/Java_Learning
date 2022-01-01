package violayu.java.spring.netty.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcfxDecoder extends ByteToMessageDecoder {
	private int length =0;
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Netty decode run");
		if(in.readableBytes() >=4) {
			if(length ==0) {
				length = in.readInt();
			}
			
			if(in.readableBytes() < length) {
				System.out.println("Readable data is less, wait");
				return;
			}
			byte[] content = new byte[length];
			if(in.readableBytes() >= length) {
				in.readBytes(content);
				RpcfxProtocol rpcfxProtocol = new RpcfxProtocol();
				rpcfxProtocol.setLength(length);
				rpcfxProtocol.setContent(content);
				out.add(rpcfxProtocol);
				
				
			}
		}
		
	}

}
