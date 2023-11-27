package com.victorb.reservapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorb.reservapp.provider.AuthProvider;
import com.victorb.reservapp.provider.model.AuthRequestDto;
import com.victorb.reservapp.provider.model.AuthResponseDto;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
	
	private @Nonnull AuthProvider authProvider;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> login (@RequestBody @Valid AuthRequestDto authRequest) {
		AuthResponseDto jwtDto = authProvider.login(authRequest);
		return ResponseEntity.ok(jwtDto);
	}

}
