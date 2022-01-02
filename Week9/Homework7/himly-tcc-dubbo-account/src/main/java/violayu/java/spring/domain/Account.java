package violayu.java.spring.domain;

import java.io.Serializable;

public class Account implements Serializable {
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCny_account() {
		return cny_account;
	}
	public void setCny_account(Long cny_account) {
		this.cny_account = cny_account;
	}
	public Long getUs_account() {
		return us_account;
	}
	public void setUs_account(Long us_account) {
		this.us_account = us_account;
	}
	private String name;
	private Long cny_account;
	private Long us_account;

}
