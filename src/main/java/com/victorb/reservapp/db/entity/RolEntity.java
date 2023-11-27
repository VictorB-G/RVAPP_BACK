package com.victorb.reservapp.db.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "RVAP_ROLES")
@Entity
public class RolEntity implements Serializable {

	private static final long serialVersionUID = -2651667076888661700L;

	@Id
	@SequenceGenerator(name = "RVAP_SQ_ROLES_GEN", sequenceName = "RVAP_SQ_ROL", allocationSize = 1)
	@GeneratedValue(generator = "RVAP_SQ_ROLES_GEN", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_ROL")
	@Getter
	@Setter
	private Long id;
	
	@Column(name = "COD_ROL")
	@Getter
	@Setter
	private String codRol;
	
	@Column(name = "DESCRIPCION")
	@Getter
	@Setter
	private String descripcion;
}
