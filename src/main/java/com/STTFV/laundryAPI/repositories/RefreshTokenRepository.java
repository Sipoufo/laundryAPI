package com.STTFV.laundryAPI.repositories;

import com.STTFV.laundryAPI.entities.RefreshToken;
import com.STTFV.laundryAPI.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Page<RefreshToken> findAllByUser(Pageable pageable, User user);

    @Modifying
    void deleteByUser(User user);
}
