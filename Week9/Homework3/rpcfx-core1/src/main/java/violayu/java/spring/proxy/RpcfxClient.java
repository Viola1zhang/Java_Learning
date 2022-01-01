package violayu.java.spring.proxy;

public interface RpcfxClient {
	<T> T create(final Class<T> serviceClass, final String url);
}
