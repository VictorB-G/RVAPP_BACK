package com.victorb.reservapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import lombok.Data;

@Data
public class Paginacion<T> {
	
	private List<T> content = new ArrayList<>();
	private PaginacionDto paginacionDto = new PaginacionDto();

	public static <T, Y> Paginacion<T> from(Page<Y> page, List<T> results) {
		Paginacion<T> paginacion = new Paginacion<>();
		paginacion.setPaginacionDto(PaginacionDto.fromPage(page));
		paginacion.setContent(results);
		return paginacion;
	}

	public static <T, Y> Paginacion<T> from(Page<Y> page, Function<? super Y, ? extends T> mapper) {
		Paginacion<T> paginacion = new Paginacion<>();
		paginacion.setPaginacionDto(PaginacionDto.fromPage(page));
		paginacion.setContent((List<T>) page.getContent().stream().map(mapper).toList());
		return paginacion;
	}
}