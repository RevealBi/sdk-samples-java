package com.pany.analytics.upmedia.reveal;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.infragistics.reveal.engine.init.RevealEngineInitializer;

@Component
@ApplicationPath("upmedia-backend/reveal-api")
public class RevealJerseyConfig extends ResourceConfig {
    public RevealJerseyConfig() {
        RevealEngineInitializer.initialize(new UpmediaAuthenticationProvider(), new UpmediaUserContextProvider(), new UpmediaDashboardProvider(), null, null);
        
        for (Class<?> clazz : RevealEngineInitializer.getClassesToRegister()) {
        	register(clazz);
        }
        register(CorsFilter.class);        
    }
}