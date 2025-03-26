package com.example.ecommerce.baseclasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserWeb {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

	@Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String usertype = "customer";
    
    public UserWeb() {
		
	}
    
	public UserWeb(Long id, String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email=email;
		this.usertype = "customer";
	}
	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getUsertype() {
		return usertype;
	}
	public String getEmail() {
		return email;
	}

}
