package redis.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import redis.data.lock.RedisLock;

@SpringBootApplication
public class RedisDataApplication {
	
	private final static String LOCK = "redis_lock";
	
	private final static int EXPIRE =3;
	private static int ammount = 10;
	
	public static void lockTest() {
		System.out.println("Lock test --- start sleep 10");
		
		if(!RedisLock.getInstance().lock(LOCK, EXPIRE)) {
			System.out.println("get lock failed");
			return;
		}
		
		try {
			Thread.sleep(10000);
			ammount -=1;
			System.out.println("inventory minus ammount:"+ammount);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		RedisLock.getInstance().releasLock(LOCK);
		System.out.println("finish lock test");
	}
	

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RedisDataApplication.class, args);
		Thread thread1 = new Thread(RedisDataApplication::lockTest);
		Thread thread2 = new Thread(RedisDataApplication::lockTest);
		
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		Thread thread3 = new Thread(RedisDataApplication::lockTest);
		thread3.start();
		thread3.join();
	}

}
