package com.galvatron.users.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        String username = successEvent.getAuthentication().getName();
        LocalDateTime timestamp = LocalDateTime.now();
        log.info("LOGIN SUCCESS - User: {}, Timestamp: {}", username, timestamp);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
        String username = failureEvent.getAuthentication().getName();
        String errorMessage = failureEvent.getException().getMessage();
        LocalDateTime timestamp = LocalDateTime.now();
        log.error("LOGIN FAILURE - User: {}, Timestamp: {}, Error: {}", username, timestamp, errorMessage);
    }
}
