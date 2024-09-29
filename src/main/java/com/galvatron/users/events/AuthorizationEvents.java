package com.galvatron.users.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class AuthorizationEvents {

    @EventListener
    public void onFailure(AuthorizationDeniedEvent deniedEvent) {
        String username = deniedEvent.getAuthentication().get().getName();
        String authorizationDecision = deniedEvent.getAuthorizationDecision().toString();
        LocalDateTime timestamp = LocalDateTime.now();
        log.error("AUTHORIZATION DENIED - User: {}, Timestamp: {}, Decision: {}", username, timestamp, authorizationDecision);
    }
}
