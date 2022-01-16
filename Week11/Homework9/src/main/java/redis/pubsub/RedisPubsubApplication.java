package redis.pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import redis.clients.jedis.JedisPool;


public class RedisPubsubApplication {

	public static void main(String[] args) {
		JedisPool jedisPool = new JedisPool();
		String channelName = "ORDER";
		SubscribeOrder subOrder = new SubscribeOrder(jedisPool, channelName);
		PublishOrder pubOrder = new PublishOrder(jedisPool, channelName);
	}

}
