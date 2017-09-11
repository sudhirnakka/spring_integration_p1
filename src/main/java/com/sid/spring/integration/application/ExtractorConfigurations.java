package com.sid.spring.integration.core;

import com.sid.spring.integration.channels.HeroChannels;
import com.sid.spring.integration.domain.AwesomeMessage;
import com.sid.spring.integration.routers.HeroMessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.dsl.http.HttpRequestHandlerEndpointSpec;
import org.springframework.integration.json.JsonToObjectTransformer;

@Configuration
public class ExtractorConfigurations {

    @Autowired
    private HeroMessageRouter heroMessageRouter;

    @Bean
    public IntegrationFlow httpInboundGatewayFlow() {
        return IntegrationFlows.from(getIncomingHeroMessages())
                .transform(jsonToObjectTransformer())
                .route(this.heroMessageRouter)
                .get();
    }

    @Bean
    JsonToObjectTransformer jsonToObjectTransformer() {
        return new JsonToObjectTransformer(AwesomeMessage.class);
    }

    private HttpRequestHandlerEndpointSpec getIncomingHeroMessages() {
        return Http.inboundGateway("/emergency/{herosecretcode}")
                .requestChannel(getRequestChannel())
                .requestMapping(r -> r
                        .methods(HttpMethod.GET)
                        .params("message"))
                .headerExpression("secretcode", "#pathVariables.herosecretcode")
                .payloadExpression("#requestParams.message[0]");
    }

    private DirectChannel getRequestChannel() {
        return MessageChannels.direct().get();
    }

}


//    private GenericHandler<Object> getCountryHandle() {
//        return (payload, headers) ->
//                "de".equals(headers.get("country"))
//                        ? "Hallo " + payload
//                        : "Hello " + payload;
//    }
