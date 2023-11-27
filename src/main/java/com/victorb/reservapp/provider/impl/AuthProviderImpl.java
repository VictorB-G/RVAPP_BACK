package com.victorb.reservapp.provider.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.victorb.reservapp.db.UsuarioRepository;
import com.victorb.reservapp.db.entity.UsuarioEntity;
import com.victorb.reservapp.provider.AuthProvider;
import com.victorb.reservapp.provider.JwtProvider;
import com.victorb.reservapp.provider.model.AuthRequestDto;
import com.victorb.reservapp.provider.model.AuthResponseDto;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthProviderImpl implements AuthProvider {

	private @Nonnull AuthenticationManager  authenticationManager;
	
	private @Nonnull UsuarioRepository usuarioRepository;
	
	private @Nonnull JwtProvider jwtProvider;
	
	@Override
	public AuthResponseDto login(@Valid AuthRequestDto authRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPass());
		
		authenticationManager.authenticate(authToken);
		
		Optional<UsuarioEntity> usuario = usuarioRepository.findByUsername(authRequest.getUsername());
		if (usuario.isEmpty()) {
			log.info("USER_NOT_FOUND");
		}
		
		String jwt = jwtProvider.generateToken(usuario.get(), generateExtraClaims(usuario.get()));
		
		return new AuthResponseDto(jwt);
	}

	private Map<String, Object> generateExtraClaims(UsuarioEntity usuarioEntity) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("nombre", usuarioEntity.getNombre());
		extraClaims.put("apellido1", usuarioEntity.getApellido1());
		extraClaims.put("apellido2", usuarioEntity.getApellido2());
		extraClaims.put("rol", usuarioEntity.getCodRol());
		return extraClaims;
	}

}
