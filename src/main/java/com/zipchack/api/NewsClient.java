package com.zipchack.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.zipchack.dto.NewsDto;


//레거시코드입니다.

@FeignClient(
		name = "Zipchack",
		url="127.0.0.1:8000",
		configuration = {
				FeignSnakeCaseConfig.class,
		}
)
public interface NewsClient {
	
	@GetMapping("/news")
	void addNewsList(); 
	

}
