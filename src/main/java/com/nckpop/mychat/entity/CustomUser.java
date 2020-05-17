package com.nckpop.mychat.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Data
@Document(collection = "user")
public class CustomUser {

    @Id
    public ObjectId _id;

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUser() {
    }

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
