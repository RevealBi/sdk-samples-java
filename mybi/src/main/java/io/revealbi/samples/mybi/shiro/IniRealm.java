package io.revealbi.samples.mybi.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;

public class IniRealm extends org.apache.shiro.realm.text.IniRealm {
	private static String iniUrl;
	
	public static void setIniUrl(String url) {
		iniUrl = url;
	}
	
	public IniRealm() {
		super(iniUrl);		
	}
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return super.supports(token);
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		AuthenticationInfo authInfo = super.doGetAuthenticationInfo(token);
		if (authInfo == null) {
			throw new IncorrectCredentialsException("Login failed");
		}
		return authInfo;
	}
}
