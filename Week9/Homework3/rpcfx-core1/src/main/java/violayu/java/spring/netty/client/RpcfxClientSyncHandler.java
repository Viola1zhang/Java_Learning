package violayu.java.spring.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import violayu.java.spring.api.RpcfxResponse;
import violayu.java.spring.netty.common.RpcfxProtocol;

import java.util.concurrent.CountDownLatch;

import com.alibaba.fastjson.JSON;

public class RpcfxClientSyncHandler extends SimpleChannelInboundHandler<RpcfxProtocol> {

	private CountDownLatch latch;
	private RpcfxResponse response;
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcfxProtocol msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Netty client receive message:");
		System.out.println("Message length: " + msg.getLength());
		System.out.println("Message content: " + new String(msg.getContent(), CharsetUtil.UTF_8));
		
		RpcfxResponse rpcfxResponse =JSON.parseObject(new String(msg.getContent(), CharsetUtil.UTF_8));
		
		response = rpcfxResponse;
		latch.countDown();
		
	}
	
	  @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        cause.printStackTrace();
	        ctx.close();
	    }
	  
	  void setLatch(CountDownLatch latch) {
		  this.latch = latch;
	  }
	  
	  RpcfxResponse getResponse() throws InterruptedException{
		  latch.await();
		  return response;
	  }

	
}
