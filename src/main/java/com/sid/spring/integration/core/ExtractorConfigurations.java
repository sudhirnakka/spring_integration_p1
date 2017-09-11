package com.sid.spring.integration.transformers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.dsl.http.HttpRequestHandlerEndpointSpec;
import org.springframework.integration.dsl.support.GenericHandler;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ExtractorConfigurations {

    @Bean
    public IntegrationFlow httpInboundGatewayFlow() {
        return IntegrationFlows.from(getInboundGatewaySpec())
                .handle(getCountryHandle())
                .handle((message, header) -> "Dumb " + message)
                .transform()
                .get();
    }

    private GenericHandler<Object> getCountryHandle() {
        return (payload, headers) ->
                "de".equals(headers.get("country"))
                        ? "Hallo " + payload
                        : "Hello " + payload;
    }

    private HttpRequestHandlerEndpointSpec getInboundGatewaySpec() {
        return Http.inboundGateway("/hello/{country}")
                .requestChannel(getRequestChannel())
                .requestMapping(r -> r
                        .methods(HttpMethod.GET)
                        .params("msg"))
                .headerExpression("country", "#pathVariables.country")
                .payloadExpression("#requestParams.msg");
    }

    private DirectChannel getRequestChannel() {
        return MessageChannels.direct("requestChannel").get();
    }

}
