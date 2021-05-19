package io.revealbi.samples.browser.shiro;

import org.apache.shiro.authc.BearerToken;

public class BearerProviderToken extends BearerToken {
	private static final long serialVersionUID = 1L;

	private String provider;
	public BearerProviderToken(String provider, String token) {
		super(token);
		this.provider = provider;
	}
	
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}		
}
