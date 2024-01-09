package com.victorb.reservapp.provider;

import java.util.Map;

import com.victorb.reservapp.db.entity.UsuarioEntity;

public interface JwtProvider {

	String generateToken(UsuarioEntity usuario, Map<String, Object> a);

	String extraerUsuario(String jwt);

}
