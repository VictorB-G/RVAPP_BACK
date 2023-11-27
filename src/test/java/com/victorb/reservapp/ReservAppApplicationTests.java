package com.victorb.reservapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservAppApplicationTests {

	@Test
	void contextLoads() {
		assertThat(new AtomicBoolean(true)).isEqualTo(new AtomicBoolean(true));
	}

}
