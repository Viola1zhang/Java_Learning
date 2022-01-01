package violayu.java.spring.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class CustomException extends RuntimeException{
	private String exceptionMessage;
	public CustomException() {
		super();
	}
	
	public CustomException(String exceptionMessage) {
		super(exceptionMessage);
		this.exceptionMessage = exceptionMessage;
	}
}
