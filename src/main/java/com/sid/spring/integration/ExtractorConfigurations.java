package com.sid.spring.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.dsl.http.HttpRequestHandlerEndpointSpec;
import org.springframework.integration.dsl.support.GenericHandler;

@Configuration
public class ExtractorConfigurations {

    @Bean
    public IntegrationFlow httpInboundGatewayFlow() {
        return IntegrationFlows.from(getInboundGatewaySpec())
                .handle(getCountryHandle())
                .handle((message, header) -> "Dumb " + message)
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
                .requestMapping(r -> r
                        .methods(HttpMethod.GET)
                        .params("msg"))
                .headerExpression("country", "#pathVariables.country")
                .payloadExpression("#requestParams.msg");
    }

}
