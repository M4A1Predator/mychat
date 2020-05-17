package com.nckpop.mychat;

import com.nckpop.mychat.util.UserRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class MychatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MychatApplication.class, args);
	}

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public UserRequest initUserRequest(){
		return new UserRequest();
	}
}
