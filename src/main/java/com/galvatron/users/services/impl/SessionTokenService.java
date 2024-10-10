package com.galvatron.users.services.impl;

import com.galvatron.users.entities.SessionToken;
import com.galvatron.users.repositories.SessionTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionTokenService {
    @Autowired
    private SessionTokenRepository sessionTokenRepository;
    public List<SessionToken> getSessionTokenListByUserId(Long userId) throws Exception {
        Optional<List<SessionToken>> sessions = sessionTokenRepository.findAllByUserId(userId);
        if(sessions.isPresent()){
            List<SessionToken> sessionTokens = sessions.get();
            return sessionTokens;
        } else {
            throw new Exception("Session not found");
        }
    }
}
