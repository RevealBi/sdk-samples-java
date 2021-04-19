package io.revealbi.samples.mybi.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class AuthenticationFilter extends PassThruAuthenticationFilter {
	private static final String AUTHENTICATE_HEADER = "WWW-Authenticate";
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (request instanceof HttpServletRequest && ((HttpServletRequest)request).getMethod().equals("OPTIONS")) {
			return true;
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}
	

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            return sendChallenge(request, response);
        }
    }
	
	protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);        
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String authcHeader =  "Bearer realm=\"mybi\"";
        httpResponse.setHeader(AUTHENTICATE_HEADER, authcHeader);
        
        if (httpRequest.getHeader("Origin") != null) {
        	httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        }
        return false;
    }
}
