package com.pany.analytics.upmedia.reveal;

import javax.ws.rs.ApplicationPath;

import org.springframework.stereotype.Component;

import com.infragistics.reveal.engine.init.InitializeParameter;
import com.infragistics.reveal.engine.init.InitializeParameterBuilder;

import io.revealbi.sdk.ext.spring.RevealBaseJerseyConfig;

@Component
@ApplicationPath("upmedia-backend/reveal-api")
public class RevealJerseyConfig extends RevealBaseJerseyConfig {
    public RevealJerseyConfig() {
        register(CorsFilter.class);        
    }

	@Override
	protected InitializeParameter getRevealInitializeParameter() {
		return new InitializeParameterBuilder().
				setAuthProvider(new UpmediaAuthenticationProvider()).
				setUserContextProvider(new UpmediaUserContextProvider()).
				setDashboardProvider(new UpmediaDashboardProvider()).
				build();
	}
    
}