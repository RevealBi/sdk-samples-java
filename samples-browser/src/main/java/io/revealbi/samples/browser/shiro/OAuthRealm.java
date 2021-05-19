package io.revealbi.samples.browser.shiro;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OAuthRealm extends AuthenticatingRealm {
	private static Logger log = Logger.getLogger(OAuthRealm.class.getName());
	private static final String OAUTH_PROVIDER_MICROSOFT = "microsoft";
	
	private List<String> validDomains;
	public OAuthRealm() {
		setAuthenticationTokenClass(BearerProviderToken.class);
	}
	
	public List<String> getValidDomains() {
		return validDomains; 
	}
	
	public void setValidDomains(List<String> domains) {
		this.validDomains = domains;
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		BearerProviderToken bearer = (BearerProviderToken)token;
		String provider = bearer.getProvider();
		String accessToken = bearer.getToken();
		if (accessToken != null) {
			AuthenticationInfo authInfo = null;
			if (provider == null || provider.equals(OAUTH_PROVIDER_MICROSOFT)) {
				authInfo = doGetMicrosoftAuthenticationInfo(accessToken);
			}
			
			if (authInfo != null) {
				validateDomain((String)authInfo.getPrincipals().getPrimaryPrincipal());
				return authInfo;
			}
		}
		throw new IncorrectCredentialsException("No access token");
	}
	
	@SuppressWarnings("unchecked")
	private AuthenticationInfo doGetMicrosoftAuthenticationInfo(String accessToken) {
		OkHttpClient client = new OkHttpClient.Builder().build();
		Request request = new Request.Builder().
				url("https://graph.microsoft.com/v1.0/me").
				method("GET", null).
				addHeader("Authorization", "Bearer " + accessToken).
				build();
		
		try {
			Response response = client.newCall(request).execute();
			Reader reader = response.body().charStream();
			HashMap<String, Object> map = new Gson().fromJson(reader, HashMap.class);
			
			String userPrincipalName = (String)map.get("userPrincipalName");
			if (userPrincipalName == null || userPrincipalName.trim().equals("")) {
				return null;
			}
			String id = (String)map.get("id");
			String displayName = (String)map.get("displayName");			
			
			SimplePrincipalCollection principals = new SimplePrincipalCollection(userPrincipalName, getName());
			if (id != null) {
				principals.add(new OAuthProviderIdPrincipal(id, userPrincipalName), getName());
			}
			if (displayName != null) {
				principals.add(new DisplayNamePrincipal(displayName), getName());
			}
			return new SimpleAuthenticationInfo(principals, accessToken);
		} catch (IOException e) {
			e.printStackTrace();
			log.log(Level.WARNING, "Failed to get graph information", e);
			throw new IncorrectCredentialsException("Failed to get account information from Graph API", e);
		}
	}	
	
	private void validateDomain(String emailAddress) throws AuthenticationException {
		if (validateDomain(emailAddress, validDomains)) {
			return;
		}
		throw new DisabledAccountException(emailAddress + " is not enabled to use this application");
	}
	
	static boolean validateDomain(String emailAddress, List<String> domains) {
		if (domains != null) {
			for (String domain : domains) {
				if (emailAddress.toLowerCase().endsWith("@" + domain.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
}
