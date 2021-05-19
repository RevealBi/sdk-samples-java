package io.revealbi.samples.browser.shiro;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.SimpleRole;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Realm implementation that provides authentication and authorization from the shiro.ini file loaded from classpath.
 * Authentication is the default one, loading users and permissions in the standard way from the INI file.
 * Authorization is the one provided by the INI file extended to assign the admin role to users authenticated through OAuth,
 * the role set in adminRoleName is set to all those users logged in through OAuth with an email address in any of the 
 * domains configured as adminDomains. 
 */
public class IniRealm extends org.apache.shiro.realm.text.IniRealm {
	private static String iniUrl;
	private List<String> adminDomains;
	private String adminRoleName;
	
	public static void setIniUrl(String url) {
		iniUrl = url;
	}
	
	public IniRealm() {
		super(iniUrl == null ? "classpath:shiro.ini" : iniUrl);
	}
	
	public List<String> getAdminDomains() {
		return adminDomains;
	}
	
	public void setAdminDomains(List<String> value) {
		this.adminDomains = value;
	}
	
	public String getAdminRoleName() {
		return adminRoleName;
	}

	public void setAdminRoleName(String adminRoleName) {
		this.adminRoleName = adminRoleName;
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
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (adminDomains != null && adminDomains.size() > 0 && adminRoleName != null && adminRoleName.trim().length() > 0) {
			Collection<OAuthProviderIdPrincipal> oAuthPrincipals = principals.byType(OAuthProviderIdPrincipal.class);
			if (!oAuthPrincipals.isEmpty()) {
				OAuthProviderIdPrincipal oAuthP = oAuthPrincipals.iterator().next();
				String email = oAuthP.getEmailAddress();
				if (email != null && OAuthRealm.validateDomain(email, adminDomains)) {
					SimpleRole adminRole = getRole(adminRoleName);
					if (adminRole != null) {
						SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo(new HashSet<String>(Arrays.asList(adminRoleName)));	
						authInfo.setObjectPermissions(adminRole.getPermissions());
						return authInfo;
					}				
				}
			}
		}
		return super.doGetAuthorizationInfo(principals);
	}
}
