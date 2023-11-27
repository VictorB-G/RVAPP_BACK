package com.victorb.reservapp.provider.model;

import lombok.Data;

@Data
public class AuthRequestDto {
	
	private String username;
	
	private String nif;
	
	private String pass;

}
