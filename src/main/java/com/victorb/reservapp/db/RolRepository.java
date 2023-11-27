package com.victorb.reservapp.db;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.victorb.reservapp.db.entity.RolEntity;

@Repository
public interface RolRepository extends ListCrudRepository<RolEntity, Long> {
	
	Optional<RolEntity> findByCodRol(String codRol);

}
