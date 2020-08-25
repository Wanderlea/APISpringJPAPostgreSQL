package spring.api.rest.model;

import java.io.Serializable;

public class UsersDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userLogin;
	private String userName;
	
	public UsersDTO(Users users) {
		
		this.userLogin = users.getLogin();
		this.userName = users.getName();
	}
	
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
