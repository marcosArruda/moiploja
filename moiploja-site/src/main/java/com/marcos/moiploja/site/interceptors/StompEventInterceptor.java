package com.marcos.moiploja.site.interceptors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.marcos.moiploja.site.web.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * Created by ive_marruda on 07/04/17.
 */
@Component
public class StompEventInterceptor implements ApplicationListener<SessionSubscribeEvent> {
    private static final Pattern p = Pattern.compile("/topic/pay/(.*)");

    @Autowired
    private ConfirmationService confirmationService;

    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        Matcher matcher = p.matcher(sha.getNativeHeader("destination").get(0));

        if(matcher.find()){
            String paymentTopic = matcher.group(1);
            confirmationService.addSubs(paymentTopic);
        }
    }
}
