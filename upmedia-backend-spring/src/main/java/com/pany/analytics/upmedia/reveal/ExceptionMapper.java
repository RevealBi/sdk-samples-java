package com.pany.analytics.upmedia.reveal;

import javax.ws.rs.core.Response;

public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        System.err.println(e);
        e.printStackTrace();
        return Response.serverError().build();
    }
}