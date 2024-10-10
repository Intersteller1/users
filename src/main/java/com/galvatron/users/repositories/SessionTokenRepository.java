package com.galvatron.users.repositories;

import com.galvatron.users.entities.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
    Optional<List<SessionToken>> findAllByUserId(Long userId);
    Optional<SessionToken> findByToken(String token);}
