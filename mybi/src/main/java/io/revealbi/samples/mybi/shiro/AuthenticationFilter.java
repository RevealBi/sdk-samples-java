package io.revealbi.samples.mybi.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;

public class AuthenticationFilter extends PassThruAuthenticationFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (request instanceof HttpServletRequest && ((HttpServletRequest)request).getMethod().equals("OPTIONS")) {
			return true;
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}
}
