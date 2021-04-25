package io.revealbi.samples.mybi.shiro;

import java.io.Serializable;

public class OAuthProviderIdPrincipal implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	public OAuthProviderIdPrincipal(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
