package ce.mnu.siteuser;

import jakarta.persistence.*;

@Entity
public class SiteUser {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long userNo;
	
	@Column(unique = true, nullable = false, length = 50)
	private String email;
	@Column(name = "uname",nullable = false, length = 30)
	private String name;
	@Column(name = "password",nullable = false, length = 50)
	private String passwd;
	
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long n) {
		userNo = n;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String e) {
		email = e;
	}
	public String getName() {
		return name;
	}
	public void setName(String n) {
		name = n;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String p) {
		passwd = p;
	}
}
