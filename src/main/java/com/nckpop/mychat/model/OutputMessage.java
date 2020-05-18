package com.nckpop.mychat.model;

import lombok.Data;

@Data
public class OutputMessage {

    private String from;
    private String body;

    public OutputMessage(String from, String body) {
        this.from = from;
        this.body = body;
    }
}
