package com.victorb.reservapp.db;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.victorb.reservapp.db.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends ListCrudRepository<UsuarioEntity, Long>{
	
	Boolean existsByUsername(String username);

	Boolean existsByNif(String nif);
	
	Optional<UsuarioEntity> findByUsername(@Param("username") String username);

	Optional<UsuarioEntity> findByNif(String nif);

	@Query("FROM UsuarioEntity u WHERE "
			+ "(:nif IS NULL OR UPPER(u.nif) LIKE UPPER(CONCAT('%',:nif,'%'))) AND "
			+ "(:rol IS NULL OR UPPER(u.codRol) = UPPER(:rol)) AND "
			+ "(:email IS NULL OR UPPER(u.email) LIKE UPPER(CONCAT('%',:email,'%'))) AND "
			+ "(:nombre IS NULL OR UPPER(u.nombre) LIKE UPPER(CONCAT('%',:nombre,'%'))) AND "
			+ "(:activo IS NULL OR u.activo = :activo)")
	Page<UsuarioEntity> filter(@Param("nif") String nif, @Param("rol") String rol, @Param("email") String email, @Param("nombre") String nombre, @Param("activo") boolean activo, Pageable pages);

}
