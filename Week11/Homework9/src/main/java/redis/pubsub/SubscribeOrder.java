package redis.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class SubscribeOrder {
	
	public SubscribeOrder(final JedisPool jedisPool, final String channelName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("start Subscribing Order");
				try(Jedis jedis = jedisPool.getResource()){
					jedis.subscribe(setupSubscribe(), channelName);
				}
			}
		}, "subscriberThread").start();
	}
	private JedisPubSub setupSubscribe() {
		return new JedisPubSub() {
			@Override
			public void onMessage(String channel,String message) {
				if(message.isEmpty()) {
					System.out.println("SubPub end");
					System.exit(0);
				}
				System.out.printf("Receive message from %s :%s\n" ,channel, message);
			}
		};
	}
}
