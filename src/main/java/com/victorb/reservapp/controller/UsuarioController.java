package com.victorb.reservapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.victorb.reservapp.provider.UsuarioProvider;
import com.victorb.reservapp.provider.model.UsuarioDto;
import com.victorb.reservapp.utils.MessageResponseDto;
import com.victorb.reservapp.utils.Paginacion;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioController {
	
	private @Nonnull UsuarioProvider usuarioProvider;

	@GetMapping("/filter")
	public MessageResponseDto<Paginacion<UsuarioDto>> filterUsers(@RequestParam(name = "nif", required = false) String nif, @RequestParam(name = "rol", required = false) String rol,
			@RequestParam(name = "email", required = false) String email, @RequestParam(name = "nombre", required = false) String nombre, @RequestParam(name = "activo", required = false) Boolean activo,
			@RequestParam(name = "page") Long page, @RequestParam(name = "itemsPerPage") Long itemsPerPage) {
		return usuarioProvider.filter(nif, rol, email, nombre, activo, page, itemsPerPage);
	}
	
	@GetMapping("/findById/{id}")
	public MessageResponseDto<UsuarioDto> findById(@PathVariable Long id) {
		return usuarioProvider.findById(id);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public MessageResponseDto<String> deleteById(@PathVariable Long id) {
		return usuarioProvider.deleteById(id);
	}
	
	@PostMapping("/save")
	public  MessageResponseDto<UsuarioDto> saveUser(@RequestBody UsuarioDto user) {
		return usuarioProvider.saveUser(user);
	}
	
	@PostMapping("/autoRegisterUser")
	public MessageResponseDto<UsuarioDto> registerUser(@RequestBody UsuarioDto user) {
		return usuarioProvider.registerUser(user);
	}

}
