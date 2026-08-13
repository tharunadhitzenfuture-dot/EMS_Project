package com.example.EMS.Service.InviteLink;

import org.springframework.http.ResponseEntity;

public interface InviteLinkService {

    String sendInvite(String email);

    ResponseEntity<?> getInviteDetails();

    String validateToken(String token);

    void removeExpiredInvites();

}