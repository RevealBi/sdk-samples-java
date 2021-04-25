package io.revealbi.samples.mybi.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.realm.Realm;

public class AuthenticationStrategy extends AbstractAuthenticationStrategy {
	@Override
	public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo info,
			AuthenticationInfo aggregate, Throwable t) throws AuthenticationException {
		
		if (t != null) {
			if (t instanceof AuthenticationException) {
				throw (AuthenticationException)t;
			} else {
				throw new AuthenticationException("Unexpected error: " + t.getMessage(), t);
			}
		}
		merge(info, aggregate);
		return aggregate;
	}
}
