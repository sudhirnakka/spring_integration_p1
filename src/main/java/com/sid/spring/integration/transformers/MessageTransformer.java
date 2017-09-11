package com.sid.spring.integration.transformers;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;

import java.util.List;

@MessageEndpoint
public class MessageTransformer {

    @Transformer(inputChannel = "requestChannelMsg")
    public String messageTransformation(String message) {
        return null;
    }

    @Transformer(inputChannel = "requestChannelMsgArray")
    public String messageTransformation(List<String> messages) {
        return null;
    }


}
