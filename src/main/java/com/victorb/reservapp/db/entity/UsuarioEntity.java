package com.victorb.reservapp.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "rvap_usuarios")
@Entity
public class UsuarioEntity implements Serializable, UserDetails{

	private static final long serialVersionUID = -6294257792587147780L;
	
	@Id
	@SequenceGenerator(name = "RVAP_SQ_USUARIOS_GEN", sequenceName = "RVAP_SQ_USUARIOS", allocationSize = 1)
	@GeneratedValue(generator = "RVAP_SQ_USUARIOS_GEN", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_usuario")
	@Getter
	@Setter
	private Long id;
	
	@Column(name = "nif")
	@Getter
	@Setter
	private String nif;
	
	@Column(name = "nombre")
	@Getter
	@Setter
	private String nombre;
	
	@Column(name = "apellido1")
	@Getter
	@Setter
	private String apellido1;
	
	@Column(name = "apellido2")
	@Getter
	@Setter
	private String apellido2;
	
	@Column(name = "email")
	@Getter
	@Setter
	private String email;
	
	@Column(name = "cod_rol")
	@Getter
	@Setter
	private String codRol;
	
	@Column(name = "activo")
	@Getter
	@Setter
	private Boolean activo;
	
	@Column(name = "username")
	@Getter
	@Setter
	private String username;
	
	@Column(name = "pass")
	@Getter
	@Setter
	private String pass;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Solo hacemos el authority del ROL ya no usaremos lista de permisos por rol para un proyecto tan pequeño
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(getCodRol()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return getPass();
	}
	
	@Override
	public boolean isEnabled() {
		return getActivo();
	}

	//Para este proyecto tan pequeño no haremos cosas con expiracion de contraseñas etc...
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
