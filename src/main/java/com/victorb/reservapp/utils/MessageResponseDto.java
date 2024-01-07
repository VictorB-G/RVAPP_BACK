package com.victorb.reservapp.utils;

import lombok.Data;

@Data
public class MessageResponseDto<T> {
	
	private MessageResponseDto(){}

	boolean success;
	String error;
	T message;

	public static <T> MessageResponseDto<T> fail(String message) {
		MessageResponseDto<T> messageResponseDto = new MessageResponseDto<>();
		messageResponseDto.setSuccess(false);
		messageResponseDto.setError(message);
		messageResponseDto.setMessage(null);
		return messageResponseDto;
	}

	public static <T> MessageResponseDto<T> success(MessageResponseDto<T> content) {
		MessageResponseDto<T> messageResponseDto = new MessageResponseDto<>();
		messageResponseDto.setSuccess(true);
		messageResponseDto.setError(null);
		messageResponseDto.setMessage(content.getMessage());
		return messageResponseDto;
	}

	public static <T> MessageResponseDto<T> success(T content) {
		MessageResponseDto<T> messageResponseDto = new MessageResponseDto<>();
		messageResponseDto.setSuccess(true);
		messageResponseDto.setError(null);
		messageResponseDto.setMessage(content);
		return messageResponseDto;
	}
}
