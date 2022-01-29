package mq.mvc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mq.mvc.messagequeue.Broker;
import mq.mvc.messagequeue.Message;

@RestController
public class MQController {
	
	private final Broker broker;
	
	public MQController (Broker broker) {
		this.broker = broker;
	}
	
	@PostMapping("/send")
	public @ResponseBody boolean send( Message message) {
		return broker.send(message.getTopic(), message.getContent());
	}
	
	@GetMapping("/poll")
	public List poll(@RequestParam(value = "topic")String topic,
					 @RequestParam(value = "rate")int rate,
					 @RequestParam(value  = "group") String group) {
		return broker.poll(topic, group, rate);
	}

}
