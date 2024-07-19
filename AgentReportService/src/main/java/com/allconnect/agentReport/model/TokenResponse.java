package com.allconnect.agentReport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="token_response",schema="allconnectutility")
public class TokenResponse {
	@Id
	
	@Column(name = "token", columnDefinition = "text")
	private String token;

	@Column(name = "expiresutc")
	private String expiresUTC;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpiresUTC() {
		return expiresUTC;
	}

	public void setExpiresUTC(String expiresUTC) {
		this.expiresUTC = expiresUTC;
	}

	@Override
	public String toString() {
		return "TokenResponse [token=" + token + ", expiresUTC=" + expiresUTC + "]";
	}

	
}
