package com.victorb.reservapp.provider.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.victorb.reservapp.db.entity.UsuarioEntity;
import com.victorb.reservapp.provider.JwtProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtProviderImp implements JwtProvider {
	
	@Value("${security.jwt.duration}")
	private long tokenDuration;
	
	@Value("${security.jwt.secret}")
	private String secret;

	@Override
	public String generateToken(UsuarioEntity usuario, Map<String, Object> extraClaims) {
		LocalDateTime issuedAt = LocalDateTime.now();
		LocalDateTime expiration = issuedAt.plusMinutes(tokenDuration);
		return Jwts
			.builder()
			.claims(extraClaims)
			.subject(usuario.getUsername())
			.issuedAt(Timestamp.valueOf(issuedAt))
			.expiration(Timestamp.valueOf(expiration))
			.header()
			.and()
			.signWith(generateSecretKey())
			.compact();		
	}
	
	private SecretKey generateSecretKey() {
		byte[] secretBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(secretBytes);
	}

	@Override
	public String extraerUsuario(String jwt) {
		return extraerClaims(jwt).getSubject();
	}

	private Claims extraerClaims(String jwt) {
		return Jwts.parser()
			.verifyWith(generateSecretKey())
			.build()
			.parseSignedClaims(jwt).getPayload();
	}
}
