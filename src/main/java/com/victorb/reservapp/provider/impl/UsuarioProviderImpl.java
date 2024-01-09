package com.victorb.reservapp.provider.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.victorb.reservapp.db.UsuarioRepository;
import com.victorb.reservapp.db.entity.UsuarioEntity;
import com.victorb.reservapp.provider.UsuarioProvider;
import com.victorb.reservapp.provider.model.UsuarioDto;
import com.victorb.reservapp.utils.Constantes;
import com.victorb.reservapp.utils.MessageResponseDto;
import com.victorb.reservapp.utils.Paginacion;
import com.victorb.reservapp.utils.PaginacionDto;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioProviderImpl implements UsuarioProvider {
	
	private @Nonnull UsuarioRepository usuarioRepository;
	
	private @Nonnull ModelMapper modelMapper;

	@Override
	public MessageResponseDto<UsuarioDto> findById(Long id) {
		if (id == null) {
			return MessageResponseDto.fail("Se ha producido un error al obtener los datos del usuario. El id a buscar no puede ser nulo");
		}
		try {
			Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(id);
			if (usuarioEntity.isEmpty()) {
				return MessageResponseDto.fail("No se encuentra ningún usuario con ese id.");
			}	
			UsuarioDto usuarioDto = modelMapper.map(usuarioEntity, UsuarioDto.class);
			return MessageResponseDto.success(usuarioDto);
		} catch (Exception e) {
			log.error(Constantes.ERROR_LITERAL_LOGS + e.getMessage());
			return MessageResponseDto.fail("Se ha producido un error al obtener los datos del usuario");
		}
	}

	@Override
	public MessageResponseDto<String> deleteById(Long id) {
		if (id == null) {
			return MessageResponseDto.fail("Se ha producido un error al eliminar el usuario. El id a buscar no puede ser nulo");
		}
		try {
			Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(id);
			if (usuarioEntity.isEmpty()) {
				return MessageResponseDto.fail("No se encuentra ningún usuario con ese id. No se puede eliminar el usuario");
			}	
			usuarioRepository.deleteById(id);
			return MessageResponseDto.success("Usuario eliminado correctamente");
		} catch (Exception e) {
			log.error(Constantes.ERROR_LITERAL_LOGS + e.getMessage());
			return MessageResponseDto.fail("Se ha producido un error al eliminar el usuario");
		}
	}

	@Override
	public MessageResponseDto<UsuarioDto> saveUser(UsuarioDto user) {
		try {
			UsuarioEntity userEnt = modelMapper.map(user, UsuarioEntity.class);
			userEnt = usuarioRepository.save(userEnt);
			UsuarioDto usuarioDto = modelMapper.map(userEnt, UsuarioDto.class);
			return MessageResponseDto.success(usuarioDto);
		} catch (Exception e) {
			log.error(Constantes.ERROR_LITERAL_LOGS + e.getMessage());
			return MessageResponseDto.fail("Se ha producido un error al guardar el usuario");
		}
	}

	@Override
	public MessageResponseDto<UsuarioDto> registerUser(UsuarioDto user) {
		try {
			UsuarioEntity userEnt = modelMapper.map(user, UsuarioEntity.class);
			userEnt.setCodRol(Constantes.COD_ROL_USER);
			userEnt = usuarioRepository.save(userEnt);
			UsuarioDto usuarioDto = modelMapper.map(userEnt, UsuarioDto.class);
			return MessageResponseDto.success(usuarioDto);
		} catch (Exception e) {
			log.error(Constantes.ERROR_LITERAL_LOGS + e.getMessage());
			return MessageResponseDto.fail("Se ha producido un error al guardar el usuario");
		}
	}

	@Override
	public MessageResponseDto<Paginacion<UsuarioDto>> filter(String nif, String rol, String email, String nombre,
			Boolean activo, Long page, Long itemsPerPage) {
		try {
			Pageable pages = PageRequest.of(page.intValue(), itemsPerPage.intValue());
			Page<UsuarioEntity> pageUsuarios = usuarioRepository.filter(nif, rol, email, nombre, activo, pages);
			if (pageUsuarios.isEmpty() && page != 0L) {
				pages = PageRequest.of(page.intValue() - 1, itemsPerPage.intValue());
				pageUsuarios = usuarioRepository.filter(nif, rol, email, nombre, activo, pages);
			}
			Paginacion<UsuarioDto> filterResponse = Paginacion.from(pageUsuarios, pageUsuarios.get().map(p -> modelMapper.map(p, UsuarioDto.class)).toList());
			return MessageResponseDto.success(filterResponse);
		} catch (Exception e) {
			log.error(Constantes.ERROR_LITERAL_LOGS + e.getMessage());
			return MessageResponseDto.fail("Se ha producido un error al filtrar el listado de usuarios");
		}
		
	}
	
}
