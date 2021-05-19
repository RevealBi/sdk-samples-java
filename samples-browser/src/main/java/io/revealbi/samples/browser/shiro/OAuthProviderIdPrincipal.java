package io.revealbi.samples.browser.shiro;

import java.io.Serializable;

public class OAuthProviderIdPrincipal implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String emailAddress;
	
	public OAuthProviderIdPrincipal(String id, String emailAddress) {
		this.id = id;
		this.emailAddress = emailAddress;
	}
	
	public String getId() {
		return id;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String toString() {
		return id;
	}
}
