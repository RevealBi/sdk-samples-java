package io.revealbi.samples.mybi.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

public class WebSessionManager extends DefaultWebSessionManager {
	private static final String SESSION_HEADER_NAME = "Shiro-Session-Id";
    private static final Log log = LogFactory.getLog(WebSessionManager.class);
	@Override
	protected void onStart(Session session, SessionContext context) {
		super.onStart(session, context);

        if (!WebUtils.isHttp(context)) {
            log.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response " +
                    "pair. No session ID cookie will be set.");
            return;

        }
        HttpServletRequest request = WebUtils.getHttpRequest(context);
        HttpServletResponse response = WebUtils.getHttpResponse(context);

        if (isSessionIdCookieEnabled()) {
            Serializable sessionId = session.getId();
            storeSessionId(sessionId, request, response);
        } else {
            log.debug("Session ID cookie is disabled.  No cookie has been set for new session with id " + session.getId());
        }

        request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
	}
	
	private void storeSessionId(Serializable currentId, HttpServletRequest request, HttpServletResponse response) {
        if (currentId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        }
        String idString = currentId.toString();
        response.addHeader(SESSION_HEADER_NAME, idString);
        log.trace("Set session ID header for session with id " + idString);
    }
	
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		if (request instanceof HttpServletRequest) {
			String headerValue = ((HttpServletRequest)request).getHeader(SESSION_HEADER_NAME);
			if (headerValue != null) {
				return headerValue;
			}
		}
		return super.getSessionId(request, response);
	}
}
