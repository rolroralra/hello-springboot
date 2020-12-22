package com.example.demo;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void test() {
		Stream.of("가","나", "라", "마", "다", "사", "바")
				.sorted(String::compareTo)
				.forEach(System.out::println);
	}

	@Test
	void contextLoads() throws JSONException, IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://nexledger.samsungsds.com:1443/testnet/authentication")
				.addHeader("Content-Type", "application/json")
				.post(RequestBody.create(
						new JSONObject()
								.put("username","shyoung.kim@samsung.com")
								.put("password", "password")
								.toString(),
						MediaType.parse("application/json"))
				)
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.toString());
		System.out.println(response.header("Set-Cookie"));
	}

}
