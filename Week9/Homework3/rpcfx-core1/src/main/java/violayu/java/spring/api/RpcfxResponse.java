package violayu.java.spring.api;

import lombok.Data;

@Data
public class RpcfxResponse {
	private Object result;
	private Boolean status;
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Exception getExcption() {
		return excption;
	}
	public void setExcption(Exception excption) {
		this.excption = excption;
	}
	private Exception excption;
	
}
