package com.victorb.reservapp.provider;

import com.victorb.reservapp.provider.model.UsuarioDto;
import com.victorb.reservapp.utils.MessageResponseDto;
import com.victorb.reservapp.utils.Paginacion;

public interface UsuarioProvider {

	MessageResponseDto<UsuarioDto> findById(Long id);

	MessageResponseDto<String> deleteById(Long id);

	MessageResponseDto<UsuarioDto> saveUser(UsuarioDto user);

	MessageResponseDto<UsuarioDto> registerUser(UsuarioDto user);

	MessageResponseDto<Paginacion<UsuarioDto>> filter(String nif, String rol, String email, String nombre,
			Boolean activo, Long page, Long itemsPerPage);

}
