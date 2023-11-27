package com.victorb.reservapp.db;

import java.util.Optional;

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

}
