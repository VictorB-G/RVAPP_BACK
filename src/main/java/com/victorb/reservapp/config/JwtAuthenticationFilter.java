package com.victorb.reservapp.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.victorb.reservapp.db.UsuarioRepository;
import com.victorb.reservapp.db.entity.UsuarioEntity;
import com.victorb.reservapp.provider.JwtProvider;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private @Nonnull UsuarioRepository usuarioRepository;
	
	private @Nonnull JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String jwt = authHeader.split(" ")[1];
		String username = jwtProvider.extraerUsuario(jwt);
		
		Optional<UsuarioEntity> usuario = usuarioRepository.findByUsername(username);
		if (usuario.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, usuario.get().getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authToken);
		
		filterChain.doFilter(request, response);	
	}

}