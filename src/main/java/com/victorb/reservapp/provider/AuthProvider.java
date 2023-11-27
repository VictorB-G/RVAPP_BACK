package com.victorb.reservapp.provider;

import com.victorb.reservapp.provider.model.AuthRequestDto;
import com.victorb.reservapp.provider.model.AuthResponseDto;

import jakarta.validation.Valid;

public interface AuthProvider {

	AuthResponseDto login(@Valid AuthRequestDto authRequest);

}
