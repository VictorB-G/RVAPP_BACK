package com.victorb.reservapp.provider.model;

import lombok.Data;

@Data
public class UsuarioDto {

	private Long id;
	
	private String nif;

	private String nombre;
	
	private String apellido1;

	private String apellido2;

	private String email;

	private RolDto rol;

	private Boolean activo;
	
}