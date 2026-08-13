package com.example.EMS.Controller.InviteLink;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.InviteRequestDTO;
import com.example.EMS.Service.InviteLink.InviteLinkService;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InviteLinkController {

	    private final InviteLinkService inviteService;


		@PostMapping("/request")
	    public ResponseEntity<?> sendInvite(@RequestBody InviteRequestDTO request) {

	        return ResponseEntity.ok(
	                inviteService.sendInvite(request.getEmail())
	        );
	    }
		
		@GetMapping("/getDetails")
		public ResponseEntity<?> getInviteDetails() {
	        return inviteService.getInviteDetails();
	    }

		
		
		@GetMapping("/validate")
		public ResponseEntity<?> validateToken(
		        @RequestParam String token) {

		    return ResponseEntity.ok(
		            inviteService.validateToken(token)
		    );
		}
}
