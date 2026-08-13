package com.example.EMS.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.Entity.InviteLink;

import jakarta.transaction.Transactional;

public interface InviteLinkRepository extends JpaRepository<InviteLink, Long> {

Optional<InviteLink> findByToken(String token);

@Transactional
void deleteByExpiryTimeBefore(LocalDateTime time);

}
