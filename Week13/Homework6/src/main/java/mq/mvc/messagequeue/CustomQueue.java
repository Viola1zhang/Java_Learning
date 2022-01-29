package mq.mvc.messagequeue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import mq.mvc.common.Constants;

public class CustomQueue {
	
	private Map<String, AtomicInteger> offset = new HashMap<>();
	
	private int writeIndex = 0;
	
	private List<String> queue = new ArrayList<>();
	
	public void put(String message) {
		synchronized (Constants.WRITE_LOCK) {
			queue.add(message);
			writeIndex +=1;
		}
	}
	
	public String get(String group) {
		int index = offset.getOrDefault(group, new AtomicInteger(-1)).incrementAndGet();
		if(writeIndex == 0 || index >=queue.size()) {
			return null;
		}
		
		return queue.get(index);
	}
	
	public boolean isEmpty() {
		return writeIndex ==0 || writeIndex >= queue.size();
	}
	
	public int size() {
		return writeIndex;
	}

}
