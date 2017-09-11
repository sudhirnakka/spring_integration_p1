package com.sid.spring.integration.routers;

import com.sid.spring.integration.channels.HeroChannels;
import com.sid.spring.integration.domain.AwesomeMessage;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

@MessageEndpoint
public class MessageRouter {

    @Router(inputChannel = HeroChannels.HERO_MESSAGE_RECIEVED)
    public String determineRouteByHero(AwesomeMessage message) {
        switch (message.getHero()) {
            case WonderWoman:
                return HeroChannels.WONDERWOMAN_CHANNEL;
            case SuperMan:
                return HeroChannels.SUPERMAN_CHANNEL;
            case SpiderMan:
                return HeroChannels.SPIDER_CHANNEL;
            case HeMan:
                return HeroChannels.HEMAN_CHANNEL;
            default:
                return null;
        }
    }

}
