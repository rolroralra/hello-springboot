package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void test() {
		Stream.of("가","나", "라", "마", "다", "사", "바")
				.sorted(String::compareTo)
				.forEach(System.out::println);
	}

}
