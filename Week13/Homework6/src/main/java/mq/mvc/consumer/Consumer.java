package mq.mvc.consumer;

import java.util.List;

public interface Consumer {
	List poll(int rate);

}
