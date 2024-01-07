package com.victorb.reservapp.utils;

import org.springframework.data.domain.Page;
import lombok.Data;

@Data
public class PaginacionDto {

	private int pages;
	private Long total;
	private int itemPerPage;

	public static <T> PaginacionDto fromPage(Page<T> page) {
		PaginacionDto paginacionDto = new PaginacionDto();
		paginacionDto.setItemPerPage(page.getNumberOfElements());
		paginacionDto.setPages(page.getTotalPages());
		paginacionDto.setTotal(page.getTotalElements());
		return paginacionDto;
	}
}