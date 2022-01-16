package redis.data.lock;
import java.util.Collections;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
public class RedisLock {
	private enum EnumSingleton{
		INSTANCE;
		private RedisLock instance;
		
		EnumSingleton(){
			instance = new RedisLock();
		}
		
		public RedisLock getSingleton() {
			return instance;
		}
	}
	
	public static RedisLock getInstance() {
		return EnumSingleton.INSTANCE.getSingleton();
	}
	
	private JedisPool jedisPool = new JedisPool();
	
	public boolean lock(String lockValue, int expireTime) {
		try(Jedis jedis = jedisPool.getResource()){
			return "OK".equals(jedis.set(lockValue, lockValue, "NX","EX", expireTime));
		}
	}
	
	public boolean releasLock(String lockKey) {
		String script ="if redis.call('get', KEYS[1]) == ARVG[1] then return redis.call('del', KEYS[1]) else return 0 end";
		
		try(Jedis jedis = jedisPool.getResource()){
			return jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockKey)).equals(1L);
		}
	}
	

}
