package com.nckpop.mychat.config;

import com.nckpop.mychat.model.StompPrincipal;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        // generate user name by UUID
        //String q = ((HttpRequest)request).getURI().getQuery();
        //var m = UriComponentsBuilder.fromUri(((HttpRequest)request).getURI()).build().getQueryParams();
        return new StompPrincipal(UUID.randomUUID().toString());
        //return new StompPrincipal(m.get("conId").toString());
    }
}
